package applications.electrostatics

import core.differential.gradient
import core.vector.DoubleVector
import core.vector.ScalarField
import core.vector.VectorField
import java.awt.Graphics
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class PointCharge(val position: DoubleVector, val charge: Double, val radius: Double) {
    val potential = ScalarField({ point ->
        coulumbs_constant * charge / (point - position).magnitude
    })
    val field = VectorField({ point -> potential.gradient(point) })

    infix fun contains(point: DoubleVector): Boolean {
        return (point - position).magnitude < radius
    }

    val lowerBound = position - DoubleVector(radius, radius)
    val upperBound = position + DoubleVector(radius, radius)

    fun draw(g: Graphics, chargeCluster: ChargeCluster) {
        val graphicalLowerBound = lowerBound.toGraphical(chargeCluster)

        g.fillOval(
                graphicalLowerBound[0].toInt(),
                graphicalLowerBound[1].toInt(),
                (radius * graphical_scale * 2).toInt(),
                (radius * graphical_scale * 2).toInt()
        )
    }

    fun surfacePoints(points: Int): List<DoubleVector> =
            (0 until points).map({ point ->
                val theta = 0 + (2 * PI) * (point.toDouble() / points)
                val pointVector = position + DoubleVector(cos(theta), sin(theta)) * radius

                pointVector
            })

    override fun toString() = "[$position, q = $charge, r = $radius]"
}