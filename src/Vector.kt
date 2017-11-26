open class Vector constructor(open vararg val dimensions: Double) {
    fun extended(targetDimensionality: Int): Vector {
        val dimensionsToAdd = Math.max(targetDimensionality - dimensionality, 0)
        return Vector(*dimensions, *DoubleArray(dimensionsToAdd))
    }

    val to3D get() = Vector3D(this)
    private val dimensionality get() = dimensions.size

    fun applyElementwise(other: Vector, operation: (Double, Double) -> Double): Vector {
        val extendedOther = other.extended(dimensionality)
        val extendedThis = extended(other.dimensionality)

        return Vector(
                *extendedOther
                        .dimensions
                        .mapIndexed({ i, dimension -> operation(extendedThis[i], dimension) })
                        .toDoubleArray()
        )
    }

    fun applyElementwise(other: Double, operation: (Double, Double) -> Double): Vector {
        return Vector(
                *this
                        .dimensions
                        .map({ dimension -> operation(dimension, other) })
                        .toDoubleArray()
        )
    }

    // Element-wise operations between vectors.
    operator fun plus(other: Vector) = applyElementwise(other, Double::plus)
    operator fun minus(other: Vector) = applyElementwise(other, Double::minus)
    operator fun times(other: Vector) = applyElementwise(other, Double::times)
    operator fun div(other: Vector) = applyElementwise(other, Double::div)

    // Apply an operation to every element and a constant scalar.
    operator fun plus(other: Double) = applyElementwise(other, Double::plus)
    operator fun minus(other: Double) = applyElementwise(other, Double::minus)
    operator fun times(other: Double) = applyElementwise(other, Double::times)
    operator fun div(other: Double) = applyElementwise(other, Double::div)

    operator fun get(index: Int): Double {
        return dimensions[index]
    }

    companion object {
        fun unit(dimensionality: Int): Vector {
            val emptyDimensions = DoubleArray(dimensionality)
            return Vector(*emptyDimensions, 1.0)
        }

        val i get() = unit(0).extended(3)
        val j get() = unit(1).extended(3)
        val k get() = unit(2).extended(3)
    }

    override fun toString(): String {
        return "<${dimensions.joinToString(separator = ", ")}>"
    }
}

interface Crossable<T> {
    infix fun cross(other: T): T
}

class Vector3D(vector: Vector) : Vector(*vector.dimensions), Crossable<Vector3D> {
    init {
        if (vector.dimensions.size != 3) {
            throw Error("Incorrect dimensionality.")
        }
    }

    override infix fun cross(other: Vector3D): Vector3D {
        return Vector(
                this[1] * other[2] - this[2] * other[1],
                -(this[0] * other[2] - this[2] * other[0]),
                this[0] * other[1] - this[1] * other[0]
        ).to3D
    }
}

open class VectorValuedFunction<in Parameter> constructor(open val function: (Parameter) -> Vector) : (Parameter) -> Vector {
    override fun invoke(parameter: Parameter): Vector {
        return function(parameter)
    }
}

fun VectorValuedFunction<Double>.differentiate(parameter: Double, delta: Double): Vector {
    return (this(parameter + delta) - this(parameter)) / (parameter - delta)
}

class VectorField constructor(override val function: (Vector) -> Vector) : VectorValuedFunction<Vector>(function)

//fun VectorValuedFunction<VectorField>.gradient(parameter: Pair<Double, Double>, delta: Double): Vector {
//    val constantXFunction = VectorValuedFunction<Double>({ (y) -> this(parameter.first, )
//
//    return (this(parameter + delta) - this(parameter)) / (parameter - delta)
//}
