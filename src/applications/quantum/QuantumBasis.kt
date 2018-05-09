package applications.quantum

import core.linear.Matrix
import core.linear.UnitaryMatrix

open class QuantumBasis(val qubits: Int, vararg val states: QuantumState) : Iterable<QuantumState> {
    override fun iterator() = states.iterator()

    constructor(matrix: UnitaryMatrix) : this(
            matrix.members.size.binaryLog,
            *(0 until matrix.cols).map { columnNumber ->
                QuantumState(matrix.column[columnNumber].vector)
            }.toTypedArray()
    )

    companion object {
        fun eyeBasis(qubits: Int) = QuantumBasis(Matrix.eye(qubits.binaryExp))
    }
}