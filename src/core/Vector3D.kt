package core

class Vector3D(vector: DoubleVector) : DoubleVector(*vector.dimensions, mandatoryArity = 3) {
    infix fun cross(other: Vector3D): Vector3D {
        return (SquareMatrix(
                arrayOf(
                        SquareMatrix.unitVectorArray,
                        this.dimensions.toTypedArray(),
                        other.dimensions.toTypedArray()
                )
        ).determinant as DoubleVector).to3D
    }
}