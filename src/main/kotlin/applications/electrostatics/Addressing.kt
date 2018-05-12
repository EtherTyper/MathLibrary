package applications.electrostatics

import core.vector.DoubleVector

fun DoubleVector.toGraphical(chargeCluster: ChargeCluster) =
        (this - chargeCluster.lowerBound) * graphical_scale

fun DoubleVector.fromGraphical(chargeCluster: ChargeCluster) =
        this / graphical_scale + chargeCluster.lowerBound