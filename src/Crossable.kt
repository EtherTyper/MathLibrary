interface Crossable<T> {
    infix fun cross(other: T): T
}

class DimensionalityError: Error("Incorrect dimensionality.")

class Vector3D(vector: Vector) : Vector(*vector.dimensions), Crossable<Vector3D> {
    init {
        if (vector.dimensions.size != 3) {
            throw DimensionalityError()
        }
    }

    override infix fun cross(other: Vector3D): Vector3D {
        return Vector(
                this[1] * other[2] - this[2] * other[1],
                -(this[0] * other[2] - this[2] * other[0]),
                this[0] * other[1] - this[1] * other[0]
        ).to3D
    }
}