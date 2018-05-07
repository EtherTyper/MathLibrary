package core.quantum

import core.complex.Complex
import core.complex.exp
import core.linear.Matrix
import core.linear.UnitaryMatrix
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class QuantumGate(val qubits: Int, members: Array<Array<out Any>>) : UnitaryMatrix(members) {
    constructor(matrix: UnitaryMatrix) : this(matrix.members.size.binaryLog, matrix.members)

    operator fun times(other: QuantumState) = QuantumState(super.times(other.column).column[0].vector)
    operator fun times(other: QuantumGate) = QuantumGate(super.times(other))

    infix fun combine(other: QuantumGate) = QuantumGate(qubits + other.qubits, (this kronecker other).members)

    companion object {
        fun identity(qubits: Int) = QuantumGate(Matrix.eye(qubits.binaryExp))

        val H = QuantumGate(
                UnitaryMatrix(arrayOf(
                        arrayOf(1.0 / sqrt(2.0), 1.0 / sqrt(2.0)),
                        arrayOf(1.0 / sqrt(2.0), -1.0 / sqrt(2.0))
                ))
        )

        val sqrtNOT = QuantumGate(
                UnitaryMatrix(arrayOf(
                        arrayOf(Complex(0.5, 0.5), Complex(0.5, -0.5)),
                        arrayOf(Complex(0.5, -0.5), Complex(0.5, 0.5))
                ))
        )

        val X = sqrtNOT * sqrtNOT

        val Y = QuantumGate(
                UnitaryMatrix(arrayOf(
                        arrayOf(Complex.`0`, -Complex.i),
                        arrayOf(Complex.i, Complex.`0`)
                ))
        )

        val Z = R(PI)

        fun R(theta: Double) = QuantumGate(
                UnitaryMatrix(arrayOf(
                        arrayOf(Complex(1.0), Complex.`0`),
                        arrayOf(Complex.`0`, exp(Complex(0.0, theta)))
                ))
        )

        val sqrtS = QuantumGate(
                UnitaryMatrix(
                        (Matrix.eye(1) directAdd sqrtNOT directAdd Matrix.eye(1)).members
                )
        )

        val S = sqrtS * sqrtS

        fun controlled(other: QuantumGate) = QuantumGate(
                UnitaryMatrix(
                        (QuantumGate.identity(other.qubits) directAdd other).members
                )
        )

        val cX = controlled(X)
        val cY = controlled(Y)
        val cZ = controlled(Z)

        val CCNOT = D(PI / 2)
        // Also, CCNOT = controlled(controlled(X)) = cX

        val cS = controlled(S)

        fun D(theta: Double) = controlled(controlled(QuantumGate(
                UnitaryMatrix(arrayOf(
                        arrayOf(Complex(0.0, cos(theta)), Complex(sin(theta))),
                        arrayOf(Complex(sin(theta)), Complex(0.0, cos(theta)))
                ))
        )))
    }
}