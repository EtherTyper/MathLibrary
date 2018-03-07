package applications.electrostatics

import core.differential.gradient
import core.vector.DoubleVector
import core.vector.ScalarField
import core.vector.VectorField

class ChargeCluster(vararg val charges: PointCharge) {
    val upperBound: DoubleVector
    val lowerBound: DoubleVector

    init {
        upperBound = DoubleVector(
                charges.map { q -> q.upperBound[0] }.max() ?: 0.0,
                charges.map { q -> q.upperBound[1] }.max() ?: 0.0
        ) + DoubleVector(5.0, 5.0)

        lowerBound = DoubleVector(
                charges.map { q -> q.lowerBound[0] }.min() ?: 0.0,
                charges.map { q -> q.lowerBound[1] }.min() ?: 0.0
        ) - DoubleVector(5.0, 5.0)
    }

    infix fun isInBounds(point: DoubleVector): Boolean {
        return charges.none { q -> q.contains(point) }
                && point[0] > lowerBound[0] && point[1] > lowerBound[1]
                && point[0] < upperBound[0] && point[1] < upperBound[1]
    }

    val potential = ScalarField({ point -> charges.map { q -> q.potential(point) }.sum() })
    val field = VectorField({ point -> potential.gradient(point) })

    override fun toString() = """
        [
            ${charges.map(PointCharge::toString).joinToString(", ")},
            lowerBound = $lowerBound, upperBound = $upperBound
        ]
    """
}