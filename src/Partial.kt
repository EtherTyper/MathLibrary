import kotlin.math.*

class DirectionalDerivative(private val direction: Vector, private val delta: Double = 0.0005) {
    init {
        if (abs(direction.magnitude - 1) > delta) {
            throw UnitVectorError(direction.magnitude)
        }
    }

    operator fun times(other: ScalarField): ScalarField = { parameter: Vector ->
        other.gradient(parameter, delta) * direction
    }

    companion object {
        // Partial derivatives
        val d_dx = DirectionalDerivative(Vector.i)
        val d_dy = DirectionalDerivative(Vector.j)
        val d_dz = DirectionalDerivative(Vector.k)
    }
}

open class Derivative(private val delta: Double = 0.0005) {
    operator fun times(other: DoubleFunction): DoubleFunction = { parameter: Double ->
        other.differentiate(parameter, delta)
    }

    companion object: Derivative()
}