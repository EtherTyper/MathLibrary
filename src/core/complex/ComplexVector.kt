package core.complex

import core.linear.Column
import core.linear.Row
import core.vector.ArityError
import core.vector.DoubleVector
import core.vector.Vector3D
import kotlin.math.max
import kotlin.math.pow

open class ComplexVector(private vararg val dimensions: Complex, mandatoryArity: Int? = null) : Number() {
    init {
        if (mandatoryArity != null && dimensions.size != mandatoryArity) {
            throw ArityError(mandatoryArity, dimensions.size)
        }
    }

    open fun extended(targetArity: Int): ComplexVector {
        val dimensionsToAdd = max(targetArity - arity, 0)

        return ComplexVector(*dimensions, *Array(dimensionsToAdd, { _ -> Complex.`0` }))
    }

    val collapseToReal: DoubleVector
        get() =
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
                        .mapIndexed({ i, dimension -> operation(extendedThis.getComplex(i), dimension) })
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
    open operator fun times(other: Double) = this * Complex(other)
    open operator fun div(other: Double) = this / Complex(other)

    // Dot products.
    operator fun times(other: ComplexVector) = conjugate.applyElementwise(other, Complex::times).dimensions.reduce(Complex::plus)

    // Outer products.
    infix fun outer(other: DoubleVector) = this.column * other.row

    fun getComplex(index: Int) = dimensions[index]

    override fun equals(other: Any?) =
            if (other is ComplexVector)
                this.dimensions.contentEquals(other.dimensions)
            else false

    override fun hashCode() = this.dimensions.map(Complex::hashCode).sum()

    override fun toString(): String = "<${dimensions.joinToString(separator = ", ")}>"

    open val conjugate get() = ComplexVector(*dimensions.map(Complex::conjugate).toTypedArray())
    open val magnitude get() = sqrt(this * this).real
    open val unit get() = this / magnitude

    infix fun angleFrom(other: ComplexVector) = acos(this * other / (this.magnitude * other.magnitude))
    infix fun projectionOnto(other: ComplexVector) = other * (this * other) / (other.magnitude.pow(2))
    infix fun rejectionFrom(other: ComplexVector) = this - (this projectionOnto other)

    // Number conformance
    override fun toByte() = magnitude.toByte()

    override fun toChar() = magnitude.toChar()
    override fun toDouble() = magnitude
    override fun toFloat() = magnitude.toFloat()
    override fun toInt() = magnitude.toInt()
    override fun toLong() = magnitude.toLong()
    override fun toShort() = magnitude.toShort()
}