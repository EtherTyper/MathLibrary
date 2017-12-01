import java.util.*

// Single-variable functions.
fun DoubleFunction.differentiate(parameter: Double, delta: Double): Double {
    return (this(parameter + delta) - this(parameter)) / delta
}

// Vector valued functions.
fun VectorValuedFunction.differentiate(parameter: Double, delta: Double): Vector {
    return (this(parameter + delta) - this(parameter)) / delta
}

fun ScalarField.directionalFunction(direction: Int, parameter: Vector): DoubleFunction {
    return { value: Double ->
        val newDimensions = Arrays.copyOf(parameter.dimensions, parameter.arity)
        newDimensions[direction] = value

        this(Vector(*newDimensions))
    }
}

// Scalar fields.
fun ScalarField.gradient(parameter: Vector, delta: Double): Vector {
    return Vector(
            *(0.until(parameter.arity)).map { dimension ->
                directionalFunction(dimension, parameter)
                        .differentiate(parameter[dimension], delta)
            }.toDoubleArray()
    )
}