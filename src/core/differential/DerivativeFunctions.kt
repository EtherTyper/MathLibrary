package core.differential

import core.vector.*

// Single-variable functions.
fun DoubleFunction.differentiate(parameter: Double, delta: Double = defaultDelta): Double {
    return (this(parameter + delta) - this(parameter)) / delta
}

// Vector valued functions.
fun VectorValuedFunction.differentiate(parameter: Double, delta: Double = defaultDelta): DoubleVector {
    return (this(parameter + delta) - this(parameter)) / delta
}

fun ScalarField.partialDerivative(direction: Int, parameter: DoubleVector, delta: Double = defaultDelta): Double {
    return this[direction, parameter]
            .differentiate(parameter[direction], delta)
}

fun ScalarField.gradient(parameter: DoubleVector, delta: Double = defaultDelta): DoubleVector {
    return DoubleVector(
            *(0.until(parameter.arity)).map { dimension ->
                partialDerivative(dimension, parameter, delta)
            }.toDoubleArray()
    )
}

fun VectorField.partialDerivative(direction: Int, parameter: DoubleVector, delta: Double = defaultDelta): DoubleVector {
    return this[direction, parameter]
            .differentiate(parameter[direction], delta)
}