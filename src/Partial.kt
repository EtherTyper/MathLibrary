import kotlin.math.*

class Directional(private val direction: Vector, private val delta: Double = 0.0005) {
    init {
        if (abs(direction.magnitude - 1) > delta) {
            throw UnitVectorError(direction.magnitude)
        }
    }

    operator fun times(other: ScalarField): ScalarField = { parameter: Vector ->
        other.gradient(parameter, delta) * direction
    }

    companion object {
        val d_dx = Directional(Vector.i)
        val d_dy = Directional(Vector.j)
        val d_dz = Directional(Vector.k)
    }
}