interface Crossable<T> {
    infix fun cross(other: T): T
}

class Vector3D(vector: DoubleVector) : DoubleVector(*vector.dimensions, mandatoryArity = 3), Crossable<Vector3D> {
    override infix fun cross(other: Vector3D): Vector3D {
        return (SquareMatrix(
                arrayOf(
                        arrayOf<Any>(DoubleVector.i, DoubleVector.j, DoubleVector.k),
                        this.dimensions.toTypedArray(),
                        other.dimensions.toTypedArray()
                )
        ).determinant as DoubleVector).to3D
    }
}