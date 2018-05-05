package core.linear

import core.MatrixDimensionError
import core.NotAMatrixError
import core.complex.Complex
import core.vector.DoubleVector
import core.vector.Multiply

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
            return Row(*Array(matrix.members[index].size,
                    { i ->
                        val value = matrix.members[index][i]
                        value as? Complex ?: Complex(value as Double)
                    }))
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

    infix fun rowCat(other: Matrix): Matrix {
        if (rows != other.rows)
            throw MatrixDimensionError("Matrices must have equal row numbers to concatenate by rows.")

        return Matrix(rows, cols + other.cols, { i, j ->
            if (j < cols)
                this[i, j]
            else
                other[i, j - cols]
        })
    }

    infix fun colCat(other: Matrix): Matrix {
        if (cols != other.cols)
            throw MatrixDimensionError("Matrices must have equal column numbers to concatenate by columns.")

        return Matrix(rows + other.rows, cols, { i, j ->
            if (i < rows)
                this[i, j]
            else
                other[i - rows, j]
        })
    }

    infix fun directAdd(other: Matrix): Matrix {
        return ((this rowCat Matrix.zeros(rows, other.cols))
                colCat (Matrix.zeros(other.rows, cols) rowCat other))
    }

    infix fun kronecker(other: Matrix): Matrix {
        return Matrix(rows * other.rows, cols * other.cols, { i, j ->
            Multiply(this[i / other.rows, j / other.cols], other[i % other.rows, j % other.cols]) as Any
        })
    }

    override fun toString(): String {
        return "| ${members.joinToString(separator = " |\n| ") { array ->
            array.joinToString(" ")
        }} |"
    }

    constructor(rows: Int, cols: Int, generator: (Int, Int) -> Any) : this(
            Array<Array<out Any>>(rows, { i ->
                Array(cols, { j -> generator(i, j) })
            })
    )

    companion object {
        fun zeros(m: Int, n: Int) =
                Matrix(m, n, { _, _ -> 0.0 })

        fun eye(n: Int) =
                UnitaryMatrix(Matrix(n, n, { i, j -> if (i == j) 1.0 else 0.0 }).members)

        val unitVectorArray get() = (0.until(3)).map(DoubleVector.Companion::unit).toTypedArray()
    }
}