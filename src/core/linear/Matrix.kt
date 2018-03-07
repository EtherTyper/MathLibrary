package core.linear

import core.vector.DoubleVector

open class Matrix(val members: Array<Array<out Any>>) {
    open val transpose: Matrix
        get() {
            if (members.isEmpty()) return this

            val newMembers = Array<Array<out Any>>(members[0].size, { j ->
                Array(members.size, { i -> members[i][j] })
            })

            return Matrix(newMembers)
        }

    override fun toString(): String {
        return "| ${members.joinToString(separator = " |\n| ") { array -> array.joinToString(" ") }} |"
    }

    companion object {
        val unitVectorArray get() = (0.until(3)).map(DoubleVector.Companion::unit).toTypedArray()
    }
}