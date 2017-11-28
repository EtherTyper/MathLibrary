// Single-variable functions.
fun ((Double) -> Double).differentiate(parameter: Double, delta: Double): Double {
    return (this(parameter + delta) - this(parameter)) / delta
}
fun ((Double) -> Double).differentiate(delta: Double) = { parameter: Double -> differentiate(parameter, delta) }

// Vector valued functions.
fun ((Double) -> Vector).differentiate(parameter: Double, delta: Double): Vector {
    return (this(parameter + delta) - this(parameter)) / delta
}

// Scalar fields.
fun ((Vector) -> Double).gradient(parameter: Vector, delta: Double): Vector {
    val constantYFunction = { x: Double -> this(Vector(x, parameter[1])) }
    val constantXFunction = { y: Double -> this(Vector(parameter[0], y)) }

    return Vector(
            constantYFunction.differentiate(parameter[0], delta),
            constantXFunction.differentiate(parameter[1], delta)
    )
}
