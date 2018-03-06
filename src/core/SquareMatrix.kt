package core

import core.vector.*

class SquareMatrix(private val members: Array<Array<out Any>>) {
    init {
        if (!members.all { array -> array.size == members.size } || members.isEmpty()) {
            throw MatrixDimensionError()
        }
    }

//    val transpose: SquareMatrix
//        get() =

    val determinant: Any
        get() {
            if (members.size == 1) {
                return members[0][0]
            } else {
                return members[0].mapIndexed({ multiplierColumn, multiplier ->
                    val minor = SquareMatrix(
                            members
                                    .filterIndexed { rowIndex, _ -> rowIndex != 0 }
                                    .map { array ->
                                        array
                                                .filterIndexed { columnIndex, _ -> columnIndex != multiplierColumn }
                                                .toTypedArray()
                                    }.toTypedArray()
                    )

                    Multiply(multiplier, minor.determinant)
                }).reduceIndexed { index, acc, value ->
                    if (index % 2 == 0)
                        Add(acc as Any, value as Any) as Any
                    else Subtract(acc as Any, value as Any) as Any
                } as Any
            }
        }

    override fun toString(): String {
        return "| ${members.joinToString(separator = " |\n| ") { array -> array.joinToString(" ") }} |"
    }

    companion object {
        val unitVectorArray get() = (0.until(3)).map(DoubleVector.Companion::unit).toTypedArray()
    }
}