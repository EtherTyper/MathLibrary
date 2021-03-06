package core.vector

import core.differential.Derivative

abstract class FunctionWrapper<in A, out B>(private val function: (A) -> B) {
    operator fun invoke(other: A): B = function(other)
    operator fun <Input> invoke(other: (Input) -> (A)) = { input: Input ->
        this(other(input))
    }
}

open class DoubleFunction(function: (Double) -> Double) : FunctionWrapper<Double, Double>(function) {
    operator fun plus(other: DoubleFunction) = DoubleFunction { a -> this(a) + other(a) }
    operator fun minus(other: DoubleFunction) = DoubleFunction { a -> this(a) - other(a) }
    operator fun times(other: DoubleFunction) = DoubleFunction { a -> this(a) * other(a) }
    operator fun div(other: DoubleFunction) = DoubleFunction { a -> this(a) / other(a) }

    operator fun plus(other: Double) = DoubleFunction { a -> this(a) + other }
    operator fun minus(other: Double) = DoubleFunction { a -> this(a) - other }
    operator fun times(other: Double) = DoubleFunction { a -> this(a) * other }
    operator fun div(other: Double) = DoubleFunction { a -> this(a) / other }

    operator fun invoke(other: DoubleFunction) = DoubleFunction(this(other::invoke))
    operator fun invoke(other: ScalarField) = ScalarField(this(other::invoke))
}

open class VectorValuedFunction(function: (Double) -> DoubleVector) : FunctionWrapper<Double, DoubleVector>(function) {
    operator fun plus(other: VectorValuedFunction) = VectorValuedFunction { a -> this(a) + other(a) }
    operator fun minus(other: VectorValuedFunction) = VectorValuedFunction { a -> this(a) - other(a) }
    operator fun times(other: VectorValuedFunction) = DoubleFunction { a -> this(a) * other(a) }
    infix fun cross(other: VectorValuedFunction) = VectorValuedFunction { a -> this(a).to3D cross other(a).to3D }

    operator fun plus(other: DoubleVector) = VectorValuedFunction { a -> this(a) + other }
    operator fun minus(other: DoubleVector) = VectorValuedFunction { a -> this(a) - other }
    operator fun times(other: DoubleVector) = DoubleFunction { a -> this(a) * other }
    infix fun cross(other: DoubleVector) = VectorValuedFunction { a -> this(a).to3D cross other.to3D }

    operator fun times(other: DoubleFunction) = VectorValuedFunction { a -> this(a) * other(a) }
    operator fun div(other: DoubleFunction) = VectorValuedFunction { a -> this(a) / other(a) }

    operator fun times(other: Double) = VectorValuedFunction { a -> this(a) * other }
    operator fun div(other: Double) = VectorValuedFunction { a -> this(a) / other }

    val unitTangent get() = VectorValuedFunction { a -> (Derivative * this)(a).unit }
    val principalUnitNormal get() = unitTangent.unitTangent

    val curvature
        get() = DoubleFunction { a ->
            (Derivative * unitTangent)(a).magnitude / (Derivative * this)(a).magnitude
        }

    operator fun invoke(other: DoubleFunction) = VectorValuedFunction(this(other::invoke))
    operator fun invoke(other: ScalarField) = VectorField(this(other::invoke))
}

open class ScalarField(function: (DoubleVector) -> Double) : FunctionWrapper<DoubleVector, Double>(function) {
    operator fun plus(other: ScalarField) = ScalarField { a -> this(a) + other(a) }
    operator fun minus(other: ScalarField) = ScalarField { a -> this(a) - other(a) }
    operator fun times(other: ScalarField) = ScalarField { a -> this(a) * other(a) }
    operator fun div(other: ScalarField) = ScalarField { a -> this(a) / other(a) }

    operator fun times(other: DoubleVector) = VectorField { a -> other * this(a) }

    operator fun plus(other: Double) = ScalarField { a -> this(a) + other }
    operator fun minus(other: Double) = ScalarField { a -> this(a) - other }
    operator fun times(other: Double) = ScalarField { a -> this(a) * other }
    operator fun div(other: Double) = ScalarField { a -> this(a) / other }

    operator fun invoke(other: VectorValuedFunction) = DoubleFunction(this { i: Double -> other(i) })
    operator fun invoke(other: VectorField) = ScalarField(this { i: DoubleVector -> other(i) })
}

open class VectorField(function: (DoubleVector) -> DoubleVector) : FunctionWrapper<DoubleVector, DoubleVector>(function) {
    operator fun plus(other: VectorField) = VectorField { a -> this(a) + other(a) }
    operator fun minus(other: VectorField) = VectorField { a -> this(a) - other(a) }
    operator fun times(other: VectorField) = ScalarField { a -> this(a) * other(a) }
    infix fun cross(other: VectorField) = VectorField { a -> this(a).to3D cross other(a).to3D }

    operator fun times(other: ScalarField) = VectorField { a -> this(a) * other(a) }
    operator fun div(other: ScalarField) = VectorField { a -> this(a) / other(a) }

    operator fun plus(other: DoubleVector) = VectorField { a -> this(a) + other }
    operator fun minus(other: DoubleVector) = VectorField { a -> this(a) - other }
    operator fun times(other: DoubleVector) = ScalarField { a -> this(a) * other }
    infix fun cross(other: DoubleVector) = VectorField { a -> this(a).to3D cross other.to3D }

    operator fun times(other: Double) = VectorField { a -> this(a) * other }
    operator fun div(other: Double) = VectorField { a -> this(a) / other }

    operator fun invoke(other: VectorValuedFunction) = VectorValuedFunction(this(other::invoke))
    operator fun invoke(other: VectorField) = VectorField(this(other::invoke))
}