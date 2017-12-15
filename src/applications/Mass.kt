package applications

import core.DoubleVector
import core.MultipleDerivative
import core.ScalarField

class Mass(field: (DoubleVector) -> Double) : ScalarField(field) {
    val density = MultipleDerivative.d_dV * this * -1.0
}