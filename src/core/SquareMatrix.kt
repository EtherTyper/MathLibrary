package core

class SquareMatrix(private val members: Array<Array<out Any>>) {
    init {
        if (!members.all { array -> array.size == members.size } || members.isEmpty()) {
            throw MatrixDimensionError()
        }
    }

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
                }).foldIndexed(initial = 0.0 as Any) { index, acc, value ->
                    if (index % 2 == 0) Add(acc, value as Any) as Any else Subtract(acc, value as Any) as Any
                }
            }
        }

    override fun toString(): String {
        return "| ${members.joinToString(separator = " |\n| ") { array -> array.joinToString(" ") }} |"
    }
}