import kotlin.math.*

open class DirectionalDerivative(private val direction: DoubleVector, private val delta: Double = defaultDelta) : Multipliable<ScalarField, ScalarField> {
    init {
        if (abs(direction.magnitude - 1) > delta) {
            throw UnitVectorError(direction.magnitude)
        }
    }

    override operator fun times(other: ScalarField): ScalarField = ScalarField { parameter: DoubleVector ->
        other.gradient(parameter, delta) * direction
    }

    @Suppress("UNUSED_PARAMETER")
    operator fun times(other: Double) = 0

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
}

open class Derivative(private val delta: Double = defaultDelta) : Multipliable<DoubleFunction, DoubleFunction> {
    override operator fun times(other: DoubleFunction): DoubleFunction = DoubleFunction { parameter: Double ->
        other.differentiate(parameter, delta)
    }

    @Suppress("UNUSED_PARAMETER")
    operator fun times(other: Double) = 0

    companion object : Derivative()
}