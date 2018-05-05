package core.vector

import core.linear.Matrix
import core.linear.SquareMatrix

class Vector3D(vector: DoubleVector) : DoubleVector(*vector.doubleDimensions, mandatoryArity = 3) {
    infix fun cross(other: Vector3D): Vector3D {
        return (SquareMatrix(
                arrayOf(
                        Matrix.unitVectorArray,
                        this.doubleDimensions.toTypedArray(),
                        other.doubleDimensions.toTypedArray()
                )
        ).determinant as DoubleVector).to3D
    }
}