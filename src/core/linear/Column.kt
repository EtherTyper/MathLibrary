package core.linear

class Column(vararg val values: Double) : Matrix(Row(*values).transpose.members) {
    override val transpose: Row
        get() = Row(*values)
}