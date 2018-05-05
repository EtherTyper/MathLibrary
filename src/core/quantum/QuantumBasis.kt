package core.quantum

import core.linear.Matrix
import core.linear.UnitaryMatrix
import kotlin.math.pow

open class QuantumBasis(vararg val states: QuantumState) : Iterable<QuantumState> {
    override fun iterator() = states.iterator()

    constructor(matrix: UnitaryMatrix) : this(*(0..matrix.cols).map { columnNumber ->
        QuantumState(matrix.column[columnNumber].vector)
    }.toTypedArray())

    companion object {
        fun eyeBasis(qubits: Int) = QuantumBasis(Matrix.eye(2.0.pow(qubits).toInt()))
    }
}