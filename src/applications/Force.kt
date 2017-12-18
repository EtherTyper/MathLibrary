package applications

import core.*

// TODO: Work once I get integrals working.
// Could map 3D position -> force, represent force on a space curve, or
// represent force on a parametric surface. The input vector is irrelevant
// until concepts like work and potential energy are implemented.
open class Force(field: (DoubleVector) -> Vector3D) : VectorField(field) {
    open infix fun torqueAbout(point: Vector3D): VectorField {
        return VectorField { parameter ->
            (parameter - point).to3D cross this(parameter).to3D
        }
    }

    infix fun accelerationOf(mass: Mass) = this / mass

    // Assuming this field represents the normal force.
    val pressure: VectorField
        get() = MultipleDerivative.d_dA * this * -1.0
}

class ConstrainedForce(private val position: VectorValuedFunction, private val field: (DoubleVector) -> Vector3D)
    : VectorValuedFunction({ t -> field(position(t)) }) {
    private val force = Force { vector -> field(vector) }

    infix fun torqueAbout(point: Vector3D): VectorValuedFunction {
        return VectorValuedFunction { t ->
            force.torqueAbout(point.to3D)(position(t))
        }
    }

    infix fun accelerationOf(mass: Mass) = this / DoubleFunction { t -> mass(position(t)) }
}