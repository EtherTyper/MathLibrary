package core.vector

import core.ArityError
import core.complex.Complex
import core.complex.ComplexVector

open class DoubleVector(vararg val doubleDimensions: Double, mandatoryArity: Int? = null) : ComplexVector(
        *doubleDimensions.map { x -> Complex(x) }.toTypedArray()
) {
    init {
        if (mandatoryArity != null && doubleDimensions.size != mandatoryArity) {
            throw ArityError(mandatoryArity, doubleDimensions.size)
        }
    }

    override fun extended(targetArity: Int) = super.extended(targetArity).collapseToReal

    operator fun plus(other: DoubleVector) = super.plus(other).collapseToReal
    operator fun minus(other: DoubleVector) = super.minus(other).collapseToReal
    override operator fun times(other: Double) = super.times(other).collapseToReal
    override operator fun div(other: Double) = super.div(other).collapseToReal
    operator fun times(other: DoubleVector) = super.times(other).collapseToReal
    infix fun realDot(other: DoubleVector) = super.realDot(other).collapseToReal

    operator fun get(index: Int) = doubleDimensions[index]

    override fun equals(other: Any?) =
            if (other is DoubleVector)
                this.doubleDimensions.contentEquals(other.doubleDimensions)
            else false

    override fun hashCode() = this.doubleDimensions.map(Double::hashCode).sum()

    companion object {
        fun unit(dimensionality: Int) = DoubleVector(*DoubleArray(dimensionality), 1.0)

        val i get() = unit(0).extended(3).to3D
        val j get() = unit(1).extended(3).to3D
        val k get() = unit(2).extended(3).to3D

        @Suppress("ObjectPropertyName")
        val `0`
            get() = DoubleVector().extended(3).to3D
    }

    override fun toString(): String = "<${doubleDimensions.joinToString(separator = ", ") { dimension ->
        "%.3f".format(dimension)
    }}>"

    override val conjugate get() = this
    override val unit get() = super.unit.collapseToReal

    infix fun angleFrom(other: DoubleVector) = super.angleFrom(other).collapseToReal
    infix fun projectionOnto(other: DoubleVector) = super.projectionOnto(other).collapseToReal
    infix fun rejectionFrom(other: DoubleVector) = super.rejectionFrom(other).collapseToReal
}

val Number.v get() = DoubleVector(this.toDouble())