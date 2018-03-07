package applications.electrostatics

import core.vector.DoubleVector

fun DoubleVector.toGraphical(chargeCluster: ChargeCluster) =
        (this - chargeCluster.lowerBound) * graphical_scale