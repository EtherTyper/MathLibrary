package applications.quantum

import core.complex.Complex
import core.complex.exp
import core.vector.Vector3D
import kotlin.math.acos

class BlochVector {
    companion object {
        fun phaseFactor(number: Complex) = exp(Complex.i * -number.argument)
        fun adjustPhase(state: QuantumState) = QuantumState(state * phaseFactor(state.getComplex(0)))

        fun theta(state: QuantumState): Double = acos(state.getComplex(0).magnitude) * 2.0
        fun phi(state: QuantumState): Double = state.getComplex(1).argument

        fun from(state: QuantumState) = Vector3D.spherical(theta(adjustPhase(state)), phi(adjustPhase(state)), 1.0)
    }
}