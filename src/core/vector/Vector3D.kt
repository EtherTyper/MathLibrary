package core.vector

import core.linear.Matrix
import core.linear.SquareMatrix
import kotlin.math.cos
import kotlin.math.sin

class Vector3D(vector: DoubleVector) : DoubleVector(*vector.doubleDimensions, mandatoryArity = 3) {
    constructor(x: Double, y: Double, z: Double) : this(DoubleVector(x, y, z))

    infix fun cross(other: Vector3D): Vector3D {
        return (SquareMatrix(
                arrayOf(
                        Matrix.unitVectorArray,
                        this.doubleDimensions.toTypedArray(),
                        other.doubleDimensions.toTypedArray()
                )
        ).determinant as DoubleVector).to3D
    }

    companion object {
        fun spherical(theta: Double, phi: Double, r: Double) = Vector3D(
                sin(theta) * cos(phi),
                sin(theta) * sin(phi),
                cos(theta)
        ) * r
    }

    override operator fun times(other: Double) = Vector3D(super.times(other))
}