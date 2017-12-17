package applications

import core.DoubleVector
import core.MultipleDerivative
import core.ScalarField

class Mass(dimensionality: Int = 2, field: (DoubleVector) -> Double) : ScalarField(field) {
    // Could be linear density, voluminous density, etc.
    val density = MultipleDerivative(dimensionality) * this * -1.0
}