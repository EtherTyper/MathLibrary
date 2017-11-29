// Single-variable functions.
fun DoubleFunction.differentiate(parameter: Double, delta: Double): Double {
    return (this(parameter + delta) - this(parameter)) / delta
}

// Vector valued functions.
fun VectorValuedFunction.differentiate(parameter: Double, delta: Double): Vector {
    return (this(parameter + delta) - this(parameter)) / delta
}

// Scalar fields.
fun ScalarField.gradient(parameter: Vector, delta: Double, arity: Int = 2): Vector {
    val constantYFunction = { x: Double -> this(Vector(x, parameter[1])) }
    val constantXFunction = { y: Double -> this(Vector(parameter[0], y)) }

    return Vector(
            constantYFunction.differentiate(parameter[0], delta),
            constantXFunction.differentiate(parameter[1], delta)
    )
}

// Vector fields.
fun VectorField.conservative(initialBound: Vector, finalBound: Vector) {

}