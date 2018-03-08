package core.linear

import core.vector.DoubleVector
import core.vector.NotAMatrixError

open class Matrix(val members: Array<Array<out Any>>) {
    init {
        if (members.map { array -> array.size }.toSet().size != 1) {
            throw NotAMatrixError()
        }
    }

    open val transpose: Matrix
        get() {
            if (members.isEmpty()) return this

            return Matrix(cols, rows, { i, j -> members[j][i] })
        }

    val rows: Int = members.size

    val cols: Int = if (rows == 0) 0 else members[0].size

    operator fun get(rowIndex: Int, colIndex: Int): Any {
        return members[rowIndex][colIndex]
    }

    class RowHelper(val matrix: Matrix) {
        operator fun get(index: Int): Row {
            @Suppress("UNCHECKED_CAST")
            return Row(*DoubleArray(matrix.members[index].size,
                    { i -> matrix.members[index][i] as Double }))
        }

        fun replace(index: Int, value: Row): Matrix {
            return Matrix(matrix.rows, matrix.cols, { i, j -> if (i == index) value[j] else matrix.members[i][j] })
        }
    }

    class ColHelper(val matrix: Matrix) {
        operator fun get(index: Int): Column {
            return matrix.transpose.row[index].transpose
        }

        fun replace(index: Int, col: Column): Matrix {
            return matrix.transpose.row.replace(index, col.transpose).transpose
        }
    }

    val row = RowHelper(this)
    val column = ColHelper(this)

    operator fun times(other: Matrix): Matrix {
        if (members.isEmpty() || other.members.isEmpty()) return this

        return Matrix(rows, other.cols, { i, j -> this.row[i].vector * other.column[j].vector })
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