package core.complex

import core.linear.Column
import core.linear.Row
import core.vector.ArityError
import core.vector.DoubleVector
import core.vector.Vector3D
import kotlin.math.acos
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sqrt

open class ComplexVector(vararg val dimensions: Complex, mandatoryArity: Int? = null) : Number() {
    init {
        if (mandatoryArity != null && dimensions.size != mandatoryArity) {
            throw ArityError(mandatoryArity, dimensions.size)
        }
    }

    fun extended(targetArity: Int): ComplexVector {
        val dimensionsToAdd = max(targetArity - arity, 0)

        return ComplexVector(*dimensions, *Array(dimensionsToAdd, { _ -> Complex.`0` }))
    }

    val collapseToReal: DoubleVector get() =
        DoubleVector(
                *dimensions.map(Complex::collapseToReal).toDoubleArray()
        )

    val to3D get() = Vector3D(collapseToReal)
    val arity get() = dimensions.size

    val column get() = Column(*collapseToReal.dimensions)
    val row get() = Row(*collapseToReal.dimensions)

    private fun applyElementwise(other: ComplexVector, operation: (Complex, Complex) -> Complex): ComplexVector {
        val extendedOther = other.extended(arity)
        val extendedThis = extended(other.arity)

        return ComplexVector(
                *extendedOther
                        .dimensions
                        .mapIndexed({ i, dimension -> operation(extendedThis[i], dimension) })
                        .toTypedArray()
        )
    }

    private fun applyElementwise(other: Complex, operation: (Complex, Complex) -> Complex): ComplexVector {
        return ComplexVector(
                *this
                        .dimensions
                        .map({ dimension -> operation(dimension, other) })
                        .toTypedArray()
        )
    }

    // Element-wise operations between vectors. (no element-wise multiplication)
    operator fun plus(other: ComplexVector) = applyElementwise(other, Complex::plus)

    operator fun minus(other: ComplexVector) = applyElementwise(other, Complex::minus)

    // Apply an operation to every element and a constant scalar.
    operator fun times(other: Complex) = applyElementwise(other, Complex::times)
    operator fun div(other: Complex) = applyElementwise(other, Complex::div)

    // Dot products.
    operator fun times(other: ComplexVector) = applyElementwise(other, Complex::times).dimensions.reduce(Double::plus)

    // Outer products.
    infix fun outer(other: DoubleVector) = this.column * other.row

    operator fun get(index: Int): Complex = dimensions[index]

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