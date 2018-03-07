package core.linear

import core.vector.DoubleVector

open class Matrix(val members: Array<Array<out Any>>) {
    open val transpose: Matrix
        get() {
            if (members.isEmpty()) return this

            return Matrix(cols, rows, { i, j -> members[j][i] })
        }

    val rows: Int = members.size

    val cols: Int = if (rows == 0) 0 else members[0].size

    fun row(index: Int): Row {
        @Suppress("UNCHECKED_CAST")
        return Row(*DoubleArray(members[index].size, { i -> members[index][i] as Double }))
    }

    fun column(index: Int): Column {
        return transpose.row(index).transpose
    }

    operator fun times(other: Matrix): Matrix {
        if (members.isEmpty() || other.members.isEmpty()) return this

        return Matrix(rows, other.cols, { i, j -> this.row(i).vector * other.column(j).vector })
    }

    override fun toString(): String {
        return "| ${members.joinToString(separator = " |\n| ") { array -> array.joinToString(" ") }} |"
    }

    constructor(rows: Int, cols: Int, generator: (Int, Int) -> Any) : this(
            Array<Array<out Any>>(rows, { i ->
                Array(cols, { j -> generator(i, j) })
            })
    )

    companion object {
        val unitVectorArray get() = (0.until(3)).map(DoubleVector.Companion::unit).toTypedArray()
    }
}