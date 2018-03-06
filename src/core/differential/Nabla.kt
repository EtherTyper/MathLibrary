package core.differential

import core.SquareMatrix
import core.vector.DoubleVector
import core.vector.ScalarField
import core.vector.VectorField
import core.vector.defaultDelta

open class Nabla constructor(private val arity: Int = 3, private val delta: Double = defaultDelta) {
    operator fun times(other: VectorField): ScalarField {
        return ScalarField { parameter ->
            (0.until(this.arity)).map { dimension ->
                other.partialDerivative(dimension, parameter, delta)[dimension]
            }.toDoubleArray().sum()
        }
    }

    operator fun times(other: DoubleVector) = this * VectorField { other }

    operator fun invoke(other: ScalarField): VectorField {
        return VectorField { parameter ->
            other.gradient(parameter, delta)
        }
    }

    infix fun cross(other: VectorField): VectorField {
        return SquareMatrix(
                arrayOf(
                        SquareMatrix.unitVectorArray,
                        (0.until(3)).map({ i -> PartialDerivative(i, delta) }).toTypedArray(),
                        (0.until(3)).map({ i -> ScalarField { t -> other(t)[i] } }).toTypedArray()
                )
        ).determinant as VectorField
    }

    // Dot products.
    operator fun get(index: DoubleVector) = DirectionalDerivative(index.unit)
    operator fun get(index: Int) = PartialDerivative(index)

    companion object : Nabla()
}