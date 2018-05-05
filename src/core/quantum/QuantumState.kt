package core.quantum

import core.UnitVectorError
import core.complex.Complex
import core.complex.ComplexVector
import core.vector.defaultDelta
import kotlin.math.abs
import kotlin.math.pow

open class QuantumState(val qubits: Double, vararg dimensions: Complex, delta: Double = defaultDelta) :
        ComplexVector(*dimensions, mandatoryArity = 2.0.pow(qubits).toInt()) {
    init {
        @Suppress("LeakingThis")
        if (abs(magnitude - 1) > delta) {
            throw UnitVectorError(magnitude)
        }
    }

    fun measure(basis: QuantumBasis): QuantumState {
        val randomNumber = Math.random()
        var sectionEnd = 0.0

        for (basisState in basis) {
            sectionEnd += (this * basisState).magnitude.pow(2.0)

            if (randomNumber <= sectionEnd) {
                return basisState
            }
        }

        throw Error("Basis not found.")
    }
}
