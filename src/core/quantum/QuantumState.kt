package core.quantum

import core.UnitVectorError
import core.complex.Complex
import core.complex.ComplexVector
import core.vector.defaultDelta
import kotlin.math.abs
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.roundToInt

val Int.binaryLog get() = (ln(this.toDouble()) / ln(2.0)).roundToInt()
val Int.binaryExp get() = 2.0.pow(this).roundToInt()

open class QuantumState(val qubits: Int, vararg dimensions: Complex, private val delta: Double = defaultDelta) :
        ComplexVector(*dimensions, mandatoryArity = qubits.binaryExp) {
    init {
        @Suppress("LeakingThis")
        if (abs(magnitude - 1) > delta) {
            throw UnitVectorError(magnitude)
        }
    }

    constructor(vector: ComplexVector) : this(vector.arity.binaryLog, *vector.dimensions)

    fun measure(basis: QuantumBasis = QuantumBasis.eyeBasis(qubits)): QuantumState {
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

    infix fun combine(other: QuantumState) = QuantumState(qubits + other.qubits,
            *(this.column kronecker other.column).vector.dimensions)

    override fun toString(): String {
        val stringComponents: MutableList<String> = mutableListOf()

        for (state in 0 until qubits.binaryExp) {
            if (dimensions[state].magnitude >= delta) {
                stringComponents.add("(${dimensions[state]})|${(1..qubits).map { qubit ->
                    state / ((qubit - 1).binaryExp) % 2
                }.joinToString("")}⟩")
            }
        }

        return stringComponents.joinToString(" + ")
    }
}
