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
            fieldPointArray.add(fieldPointArray.last() - scalarField.gradient(fieldPointArray.last()).unit / 100.0)
            if (scalarField(fieldPointArray.last()) - scalarField(isoclinePointArray.last()) > isoclineSignificance)
                isoclinePointArray.add(fieldPointArray.last())
        }
    }
}

// This only works for scalar fields of 2D inputs.
class Isocline(startPoint: DoubleVector,
               scalarField: ScalarField,
               inBounds: (DoubleVector) -> Boolean) {
    val pointArray: MutableList<DoubleVector> = mutableListOf()

    init {
        val counterclockwisePoints: MutableList<DoubleVector> = mutableListOf(startPoint)
        val clockwisePoints: MutableList<DoubleVector> = mutableListOf(startPoint)

        while (inBounds(counterclockwisePoints.last())) {
            counterclockwisePoints.add(
                    nextPoint(
                            counterclockwisePoints.first(),
                            counterclockwisePoints.last(),
                            1.0,
                            scalarField
                    )
            )
        }

        while (inBounds(clockwisePoints.last())) {
            clockwisePoints.add(
                    nextPoint(
                            clockwisePoints.first(),
                            clockwisePoints.last(),
                            -1.0,
                            scalarField
                    )
            )
        }

        pointArray.addAll(clockwisePoints.asReversed())
        pointArray.addAll(counterclockwisePoints)
    }

    companion object {
        fun nextPoint(startPoint: DoubleVector, lastPoint: DoubleVector, direction: Double, scalarField: ScalarField): DoubleVector {
            val gradient = scalarField.gradient(lastPoint)
            val zeroDerivative = DoubleVector(-gradient[1], gradient[0]) * direction

            val error = scalarField(startPoint) / scalarField(lastPoint) - 1
            if (error > 0.2) {
                return lastPoint - gradient.unit / 10.0
            } else if (error < -0.2) {
                return lastPoint + gradient.unit / 10.0
            } else {
                return lastPoint + zeroDerivative.unit / 100.0
            }
        }
    }
}