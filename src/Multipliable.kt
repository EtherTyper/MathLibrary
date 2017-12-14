interface Crossable<T> {
    infix fun cross(other: T): T
}

class Vector3D(vector: DoubleVector) : DoubleVector(*vector.dimensions, mandatoryArity = 3), Crossable<Vector3D> {
    override infix fun cross(other: Vector3D): Vector3D {
        return DoubleVector(
                this[1] * other[2] - this[2] * other[1],
                -(this[0] * other[2] - this[2] * other[0]),
                this[0] * other[1] - this[1] * other[0]
        ).to3D
    }
}