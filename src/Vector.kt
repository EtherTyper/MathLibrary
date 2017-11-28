open class Vector constructor(open vararg val dimensions: Double) : Number() {
    fun extended(targetDimensionality: Int): Vector {
        val dimensionsToAdd = Math.max(targetDimensionality - dimensionality, 0)
        return Vector(*dimensions, *DoubleArray(dimensionsToAdd))
    }

    val to3D get() = Vector3D(this)
    private val dimensionality get() = dimensions.size

    private fun applyElementwise(other: Vector, operation: (Double, Double) -> Double): Vector {
        val extendedOther = other.extended(dimensionality)
        val extendedThis = extended(other.dimensionality)

        return Vector(
                *extendedOther
                        .dimensions
                        .mapIndexed({ i, dimension -> operation(extendedThis[i], dimension) })
                        .toDoubleArray()
        )
    }

    private fun applyElementwise(other: Double, operation: (Double, Double) -> Double): Vector {
        return Vector(
                *this
                        .dimensions
                        .map({ dimension -> operation(dimension, other) })
                        .toDoubleArray()
        )
    }

    // Element-wise operations between vectors. (no element-wise multiplication)
    operator fun plus(other: Vector) = applyElementwise(other, Double::plus)

    operator fun minus(other: Vector) = applyElementwise(other, Double::minus)
    operator fun div(other: Vector) = applyElementwise(other, Double::div)

    // Apply an operation to every element and a constant scalar.
    operator fun plus(other: Double) = applyElementwise(other, Double::plus)

    operator fun minus(other: Double) = applyElementwise(other, Double::minus)
    operator fun times(other: Double) = applyElementwise(other, Double::times)
    operator fun div(other: Double) = applyElementwise(other, Double::div)

    // Dot products.
    operator fun times(other: Vector) = applyElementwise(other, Double::times).dimensions.reduce(Double::plus)

    operator fun get(index: Int): Double = dimensions[index]

    companion object {
        fun unit(dimensionality: Int) = Vector(*DoubleArray(dimensionality), 1.0)

        val i get() = unit(0).extended(3).to3D
        val j get() = unit(1).extended(3).to3D
        val k get() = unit(2).extended(3).to3D
    }

    override fun toString(): String = "<${dimensions.joinToString(separator = ", ")}>"

    val magnitude get() = Math.sqrt(this * this)

    // Number conformance
    override fun toByte() = magnitude.toByte()

    override fun toChar() = magnitude.toChar()
    override fun toDouble() = magnitude
    override fun toFloat() = magnitude.toFloat()
    override fun toInt() = magnitude.toInt()
    override fun toLong() = magnitude.toLong()
    override fun toShort() = magnitude.toShort()
}

val Number.v get() = Vector(this.toDouble())