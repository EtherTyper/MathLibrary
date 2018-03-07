package core.differential

import core.vector.DoubleVector
import core.vector.ScalarField

class DescentLine(isoclineSignificance: Double,
                  startPoint: DoubleVector,
                  scalarField: ScalarField,
                  inBounds: (DoubleVector) -> Boolean) {
    val isoclinePointArray: MutableList<DoubleVector> = mutableListOf(startPoint)
    val fieldPointArray: MutableList<DoubleVector> = mutableListOf(startPoint)

    init {
        while (inBounds(fieldPointArray.last())) {
            fieldPointArray.add(fieldPointArray.last() + scalarField.gradient(fieldPointArray.last()).unit / 100.0)
            if (scalarField(fieldPointArray.last()) - scalarField(isoclinePointArray.last()) > isoclineSignificance)
                isoclinePointArray.add(fieldPointArray.last())
        }
    }
}