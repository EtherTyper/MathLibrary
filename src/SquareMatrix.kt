class SquareMatrix(private val members: Array<Array<Double>>) {
    init {
        if (!members.all { array -> array.size == members.size } || members.isEmpty()) {
            throw MatrixDimensionError()
        }
    }

    val determinant: Double
        get() {
            if (members.size == 1) {
                return members[0][0]
            } else {
                return members[0].mapIndexed({ multiplierColumn, multiplier: Double ->
                    val minor = SquareMatrix(
                            members
                                    .filterIndexed { rowIndex, _ -> rowIndex != 0 }
                                    .map { array ->
                                        array
                                                .filterIndexed { columnIndex, _ -> columnIndex != multiplierColumn }
                                                .toTypedArray()
                                    }.toTypedArray()
                    )

                    multiplier * minor.determinant
                }).foldIndexed(initial = 0.0) { index, acc, value ->
                    if (index % 2 == 0) acc + value else acc - value
                }
            }
        }

    override fun toString(): String {
        return members.joinToString(separator = "\n") { array -> array.joinToString(" ") }
    }
}