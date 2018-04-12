package core.linear

import core.vector.*

class SquareMatrix(members: Array<Array<out Any>>) : Matrix(members) {
    init {
        if (!members.all { array -> array.size == members.size } || members.isEmpty()) {
            throw SquareMatrixDimensionError()
        }
    }

    override val transpose get() = SquareMatrix(super.transpose.members)

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

    companion object {
        fun zeros(m: Int, n: Int): Matrix =
                Matrix(m, n, { _, _ -> 0 })

        fun eye(n: Int): Matrix =
                Matrix(n, n, { i, j -> if (i == j) 1 else 0 })
    }
}