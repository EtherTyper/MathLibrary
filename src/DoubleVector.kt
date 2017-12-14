import kotlin.math.sqrt

open class DoubleVector constructor(vararg val dimensions: Double, mandatoryArity: Int? = null) : Number() {
    init {
        if (mandatoryArity != null && dimensions.size != mandatoryArity) {
            throw ArityError(mandatoryArity, dimensions.size)
        }
    }

    fun extended(targetArity: Int): DoubleVector {
        val dimensionsToAdd = Math.max(targetArity - arity, 0)
        return DoubleVector(*dimensions, *DoubleArray(dimensionsToAdd))
    }

    val to3D get() = Vector3D(this)
    val arity get() = dimensions.size

    private fun applyElementwise(other: DoubleVector, operation: (Double, Double) -> Double): DoubleVector {
        val extendedOther = other.extended(arity)
        val extendedThis = extended(other.arity)

        return DoubleVector(
                *extendedOther
                        .dimensions
                        .mapIndexed({ i, dimension -> operation(extendedThis[i], dimension) })
                        .toDoubleArray()
        )
    }

    private fun applyElementwise(other: Double, operation: (Double, Double) -> Double): DoubleVector {
        return DoubleVector(
                *this
                        .dimensions
                        .map({ dimension -> operation(dimension, other) })
                        .toDoubleArray()
        )
    }

    // Element-wise operations between vectors. (no element-wise multiplication)
    operator fun plus(other: DoubleVector) = applyElementwise(other, Double::plus)

    operator fun minus(other: DoubleVector) = applyElementwise(other, Double::minus)
    operator fun div(other: DoubleVector) = applyElementwise(other, Double::div)

    // Apply an operation to every element and a constant scalar.
    operator fun plus(other: Double) = applyElementwise(other, Double::plus)

    operator fun minus(other: Double) = applyElementwise(other, Double::minus)
    operator fun times(other: Double) = applyElementwise(other, Double::times)
    operator fun div(other: Double) = applyElementwise(other, Double::div)

    // Dot products.
    operator fun times(other: DoubleVector) = applyElementwise(other, Double::times).dimensions.reduce(Double::plus)

    operator fun get(index: Int): Double = dimensions[index]

    companion object {
        fun unit(dimensionality: Int) = DoubleVector(*DoubleArray(dimensionality), 1.0)

        val i get() = unit(0).extended(3).to3D
        val j get() = unit(1).extended(3).to3D
        val k get() = unit(2).extended(3).to3D

        @Suppress("ObjectPropertyName")
        val `0`
            get() = DoubleVector().extended(3).to3D
        val zero get() = DoubleVector().extended(3).to3D
    }

    override fun toString(): String = "<${dimensions.joinToString(separator = ", ")}>"

    val magnitude get() = sqrt(this * this)

    // Number conformance
    override fun toByte() = magnitude.toByte()

    override fun toChar() = magnitude.toChar()
    override fun toDouble() = magnitude
    override fun toFloat() = magnitude.toFloat()
    override fun toInt() = magnitude.toInt()
    override fun toLong() = magnitude.toLong()
    override fun toShort() = magnitude.toShort()
}

val Number.v get() = DoubleVector(this.toDouble())