open class Nabla constructor(val arity: Int = 3, private val delta: Double = defaultDelta) {
    operator fun times(other: VectorField): ScalarField {
        return ScalarField { parameter ->
            (0.until(this.arity)).map { dimension ->
                other.partialDerivative(dimension, parameter)[dimension]
            }.toDoubleArray().sum()
        }
    }

    operator fun invoke(other: ScalarField): VectorField {
        return VectorField { parameter ->
            other.gradient(parameter)
        }
    }

    // Dot products.
    operator fun get(index: Int): DirectionalDerivative = DirectionalDerivative(DoubleVector.unit(index))

    companion object : Nabla()
}