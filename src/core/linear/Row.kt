package core.linear

class Row(vararg val values: Double) : Matrix(arrayOf(values.toTypedArray())) {
    override val transpose: Column
        get() = Column(*values)
}