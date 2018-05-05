package core.differential

import core.UnitVectorError
import core.vector.*
import kotlin.math.abs

open class DirectionalDerivative(private val direction: DoubleVector, private val delta: Double = defaultDelta) {
    init {
        if (abs(direction.magnitude - 1) > delta) {
            throw UnitVectorError(direction.magnitude)
        }
    }

    operator fun times(other: ScalarField) = ScalarField { parameter: DoubleVector ->
        other.gradient(parameter, delta) * direction
    }

    @Suppress("UNUSED_PARAMETER")
    operator fun times(other: Double) = 0.0

    @Suppress("unused")
    companion object {
        // Partial derivatives
        val d_dx = PartialDerivative(0)
        val d_dy = PartialDerivative(1)
        val d_dz = PartialDerivative(2)
    }
}

class PartialDerivative(private val direction: Int, private val delta: Double = defaultDelta)
    : DirectionalDerivative(DoubleVector.unit(direction), delta) {
    operator fun times(other: VectorField): VectorField = VectorField { parameter: DoubleVector ->
        other.partialDerivative(direction, parameter, delta)
    }

    operator fun times(other: DoubleVector) = DoubleVector(*DoubleArray(other.arity))
}

class MultipleDerivative(private vararg val dimensions: Int) {
    constructor(numDimensions: Int) : this(*(0.until(numDimensions)).toList().toIntArray())

    operator fun times(other: VectorField): VectorField =
            dimensions.map { i -> PartialDerivative(i) }.fold(other) { acc, partial -> partial * acc }

    operator fun times(other: ScalarField): ScalarField =
            dimensions.map { i -> PartialDerivative(i) }.fold(other) { acc, partial -> partial * acc }

    companion object {
        // Area and volume derivatives
        val d_dA = MultipleDerivative(2)
        val d_dV = MultipleDerivative(3)
    }
}

open class Derivative(private val delta: Double = defaultDelta) {
    operator fun times(other: VectorValuedFunction) = VectorValuedFunction { parameter: Double ->
        other.differentiate(parameter, delta)
    }

    operator fun times(other: DoubleFunction) = DoubleFunction { parameter: Double ->
        other.differentiate(parameter, delta)
    }

    @Suppress("UNUSED_PARAMETER")
    operator fun times(other: Double) = 0.0

    companion object : Derivative()
}