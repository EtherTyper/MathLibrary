package core.vector

import core.linear.Column
import core.linear.Row
import kotlin.math.acos
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sqrt

open class DoubleVector constructor(vararg val dimensions: Double, mandatoryArity: Int? = null) : Number() {
    init {
        if (mandatoryArity != null && dimensions.size != mandatoryArity) {
            throw ArityError(mandatoryArity, dimensions.size)
        }
    }

    fun extended(targetArity: Int): DoubleVector {
        val dimensionsToAdd = max(targetArity - arity, 0)
        return DoubleVector(*dimensions, *DoubleArray(dimensionsToAdd))
    }

    val to3D get() = Vector3D(this)
    val arity get() = dimensions.size

    val column get() = Column(*dimensions)
    val row get() = Row(*dimensions)

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

    // Apply an operation to every element and a constant scalar.
    operator fun times(other: Double) = applyElementwise(other, Double::times)

    operator fun div(other: Double) = applyElementwise(other, Double::div)

    // Dot products.
    operator fun times(other: DoubleVector) = applyElementwise(other, Double::times).dimensions.reduce(Double::plus)

    // Outer products.
    infix fun outer(other: DoubleVector) = this.column * other.row

    operator fun get(index: Int): Double = dimensions[index]

    override fun equals(other: Any?) =
            if (other is DoubleVector)
                this.dimensions.contentEquals(other.dimensions)
            else false

    override fun hashCode() = this.dimensions.map(Double::hashCode).sum()

    companion object {
        fun unit(dimensionality: Int) = DoubleVector(*DoubleArray(dimensionality), 1.0)

        val i get() = unit(0).extended(3).to3D
        val j get() = unit(1).extended(3).to3D
        val k get() = unit(2).extended(3).to3D

        @Suppress("ObjectPropertyName")
        val `0`
            get() = DoubleVector().extended(3).to3D
        val zero get() = `0`
    }

    override fun toString(): String = "<${dimensions.joinToString(separator = ", ")}>"

    val magnitude get() = sqrt(this * this)
    val unit get() = this / magnitude

    infix fun angleFrom(other: DoubleVector) = acos(this * other / (this.magnitude * other.magnitude))
    infix fun projectionOnto(other: DoubleVector) = other * (this * other) / (other.magnitude.pow(2))
    infix fun rejectionFrom(other: DoubleVector) = this - (this projectionOnto other)

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