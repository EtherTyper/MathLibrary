package applications

import core.*

// TODO: Work once I get integrals working.
class Force(field: (DoubleVector) -> Vector3D) : VectorField(field) {
    infix fun torqueAbout(point: Vector3D): VectorField {
        return VectorField { parameter ->
            (parameter - point).to3D cross this(parameter).to3D
        }
    }

    infix fun accelerationOf(mass: Mass) = this / mass

    // Assuming this field represents the normal force.
    val pressure = MultipleDerivative.d_dA * this * -1.0
}

class ConstrainedForce(val position: VectorValuedFunction, field: (Double) -> Vector3D) : VectorValuedFunction(field) {
    infix fun torqueAbout(point: Vector3D): VectorValuedFunction {
        return VectorValuedFunction { parameter ->
            (position(parameter) - point).to3D cross this(parameter).to3D
        }
    }

    infix fun accelerationOf(mass: Mass) = this / DoubleFunction { t -> mass(position(t)) }
}