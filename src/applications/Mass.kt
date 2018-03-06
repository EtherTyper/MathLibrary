package applications

import core.vector.DoubleVector
import core.differential.MultipleDerivative
import core.vector.ScalarField

class Mass(dimensionality: Int = 2, field: (DoubleVector) -> Double) : ScalarField(field) {
    // Could be linear density, voluminous density, etc.
    val density = MultipleDerivative(dimensionality) * this * -1.0
}