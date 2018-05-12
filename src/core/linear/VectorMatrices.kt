package core.linear

import core.complex.Complex
import core.complex.ComplexVector

// These are not subclasses of ComplexVector since we want * to default to matrix
// multiplication and not the Hermitian dot product.
class Column(vararg val values: Complex) : Matrix(values.size, 1, { i, _ -> values[i] }) {
    override val transpose: Row
        get() = Row(*values)

    infix fun kronecker(other: Column) = super.kronecker(other).column[0]

    constructor(vararg values: Double) : this(*values.map { i -> Complex(i) }.toTypedArray())

    operator fun get(index: Int) = values[index]

    val vector get() = ComplexVector(*values)
    val doubleVector get() = vector.collapseToReal
}

class Row(vararg val values: Complex) : Matrix(arrayOf(values)) {
    override val transpose: Column
        get() = Column(*values)

    infix fun kronecker(other: Row) = super.kronecker(other).row[0]

    constructor(vararg values: Double) : this(*values.map { i -> Complex(i) }.toTypedArray())

    operator fun get(index: Int) = values[index]

    val vector get() = ComplexVector(*values)
    val doubleVector get() = vector.collapseToReal
}