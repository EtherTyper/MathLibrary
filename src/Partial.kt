import kotlin.math.*

class DirectionalDerivative(private val direction: Vector, private val delta: Double = 0.0005): Multipliable<ScalarField, ScalarField> {
    init {
        if (abs(direction.magnitude - 1) > delta) {
            throw UnitVectorError(direction.magnitude)
        }
    }

    override operator fun times(other: ScalarField): ScalarField = { parameter: Vector ->
        other.gradient(parameter, delta) * direction
    }

    @Suppress("UNUSED_PARAMETER")
    operator fun times(other: Vector) = 0

    companion object {
        // Partial derivatives
        val d_dx = DirectionalDerivative(Vector.i)
        val d_dy = DirectionalDerivative(Vector.j)
        val d_dz = DirectionalDerivative(Vector.k)
    }
}

open class Derivative(private val delta: Double = 0.0005): Multipliable<DoubleFunction, DoubleFunction> {
    override operator fun times(other: DoubleFunction): DoubleFunction = { parameter: Double ->
        other.differentiate(parameter, delta)
    }

    @Suppress("UNUSED_PARAMETER")
    operator fun times(other: Double) = 0

    companion object: Derivative()
}