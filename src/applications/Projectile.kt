package applications

import core.Derivative
import core.DoubleVector
import core.VectorValuedFunction
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

class Projectile(h: Double = 0.0, v_0: Double, theta: Double = PI, g: Double = 9.8)
    : VectorValuedFunction({ t ->
    DoubleVector(
            v_0 * cos(theta) * t,
            h + v_0 * sin(theta) * t - 0.5 * g * t.pow(2))
}) {
    val velocity = Derivative * this // (v_0 * cos(theta)) * i + (v_0 * sin(theta) - gt) * j
    val acceleration = Derivative * velocity // -gt * j
}