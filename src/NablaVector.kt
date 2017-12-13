open class NablaVector constructor(val arity: Int = 3, private val delta: Double = defaultDelta) {
    operator fun times(other: VectorField): ScalarField {
        return ScalarField { parameter ->
            (0.until(this.arity)).map { dimension ->
                other[dimension, parameter]
                        .differentiate(parameter[dimension], delta)[dimension]
            }.toDoubleArray().sum()
        }
    }

    // Dot products.
    operator fun get(index: Int): DirectionalDerivative = DirectionalDerivative(DoubleVector.unit(index))

    companion object : NablaVector()
}