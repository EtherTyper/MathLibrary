package core.linear

import core.vector.DoubleVector

// These are not subclasses of DoubleVector since we want * to default to matrix
// multiplication and not
class Column(vararg val values: Double) : Matrix(values.size, 1, { i, _ -> values[i] }) {
    override val transpose: Row
        get() = Row(*values)

    val vector get() = DoubleVector(*values)
}

class Row(vararg val values: Double) : Matrix(arrayOf(values.toTypedArray())) {
    override val transpose: Column
        get() = Column(*values)

    val vector get() = DoubleVector(*values)
}