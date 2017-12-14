package applications

import core.DoubleVector
import core.Vector3D
import core.VectorField

class Force(field: (DoubleVector) -> Vector3D) : VectorField(field) {
    infix fun torqueAbout(point: Vector3D): VectorField {
        return VectorField { parameter ->
            (parameter - point).to3D cross this(parameter).to3D
        }
    }
}