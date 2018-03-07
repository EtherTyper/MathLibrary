package applications

import core.differential.Derivative
import core.vector.DoubleVector
import core.vector.VectorValuedFunction
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

class Projectile(val h: Double = 0.0, val v_0: Double, val theta: Double = PI, val g: Double = 9.8)
    : VectorValuedFunction({ t ->
    DoubleVector(
            v_0 * cos(theta) * t,
            h + v_0 * sin(theta) * t - 0.5 * g * t.pow(2))
}) {
    val velocity = Derivative * this // (v_0 * cos(theta)) * i + (v_0 * sin(theta) - gt) * j
    val acceleration = (Derivative * velocity)(0.0) // -gt * j (Constant)

    infix fun gravityOn(other: Mass) = ConstrainedForce(this) { position: DoubleVector ->
        (acceleration * other(position)).extended(3).to3D
    }

    override fun toString(): String {
        return """Projectile with an initial height of $h meters, velocity of $v_0 m/s, angle of
            |$theta radians, and a constant acceleration due to gravity of $g m/s^2.""".trimMargin()
    }
}