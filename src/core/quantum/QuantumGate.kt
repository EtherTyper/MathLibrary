package core.quantum

import core.linear.UnitaryMatrix

class QuantumGate(members: Array<Array<out Any>>): UnitaryMatrix(members) {
    constructor(matrix: UnitaryMatrix): this(matrix.members)

    operator fun times(other: QuantumState) = QuantumState(super.times(other.column).column[0].vector)

    infix fun combine(other: QuantumGate) = QuantumGate((this kronecker other).members)
}