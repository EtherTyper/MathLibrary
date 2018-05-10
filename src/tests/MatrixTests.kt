package tests

import core.linear.Column
import core.linear.Matrix
import core.linear.Row
import core.linear.SquareMatrix

class MatrixTests : Tests() {
    companion object {
        @JvmStatic
        fun main(vararg args: String) {
            section("Matrices and Determinants") {
                val twoByTwo = SquareMatrix(arrayOf(arrayOf(1.0, 3.0), arrayOf(1.0, 4.0)))
                val threeByThree = SquareMatrix(arrayOf(arrayOf(0.0, 1.0, 2.0), arrayOf(3.0, 4.0, 5.0), arrayOf(6.0, 7.0, 8.0)))

                println("$twoByTwo = ${twoByTwo.determinant}")
                println()
                println("$threeByThree = ${threeByThree.determinant}")
                println()
                println("$threeByThree' = \n\n${threeByThree.transpose}")
                println()
                println("R2 = ${threeByThree.row[1]}")
                println()
                println("A where R2 is replaced = \n\n${threeByThree.row.replace(1, Row(10.0, 20.0, 30.0))}")
                println()
                println("C3 = \n${threeByThree.column[2]}")
                println()
                println("A where C3 is replaced = \n\n${threeByThree.column.replace(2, Column(10.0, 20.0, 30.0))}")
                println()
                println("This should error, as the matrix is misshapen.")
                shouldError { Matrix(arrayOf(arrayOf(1, 2, 3), arrayOf(5, 6, 7, 8))) }
                println()
                println("This should error, as the matrix isn't square.")
                shouldError { SquareMatrix(arrayOf(arrayOf(1, 2, 3, 4), arrayOf(5, 6, 7, 8))) }
                println()
                println("(Direct sum)\nA ⊕ I_4 = \n${threeByThree directAdd Matrix.eye(4)}")
            }
        }
    }
}