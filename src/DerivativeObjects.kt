import kotlin.math.*

class DirectionalDerivative(private val direction: DoubleVector, private val delta: Double = defaultDelta): Multipliable<ScalarField, ScalarField> {
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
        val d_dx = DirectionalDerivative(DoubleVector.i)
        val d_dy = DirectionalDerivative(DoubleVector.j)
        val d_dz = DirectionalDerivative(DoubleVector.k)
    }
}

open class Derivative(private val delta: Double = defaultDelta): Multipliable<DoubleFunction, DoubleFunction> {
    override operator fun times(other: DoubleFunction): DoubleFunction = DoubleFunction { parameter: Double ->
        other.differentiate(parameter, delta)
    }

    @Suppress("UNUSED_PARAMETER")
    operator fun times(other: Double) = 0

    companion object: Derivative()
}