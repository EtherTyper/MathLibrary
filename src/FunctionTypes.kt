abstract class FunctionWrapper<in A, out B>(private val function: (A) -> B) {
    operator fun invoke(a: A): B = function(a)
}

class DoubleFunction(function: (Double) -> Double): FunctionWrapper<Double, Double>(function) {
    operator fun plus(other: DoubleFunction) = DoubleFunction { a -> this(a) + other(a) }
    operator fun minus(other: DoubleFunction) = DoubleFunction { a -> this(a) - other(a) }
    operator fun times(other: DoubleFunction) = DoubleFunction { a -> this(a) * other(a) }
    operator fun div(other: DoubleFunction) = DoubleFunction { a -> this(a) / other(a) }

    operator fun plus(other: Double) = DoubleFunction { a -> this(a) + other }
    operator fun minus(other: Double) = DoubleFunction { a -> this(a) - other }
    operator fun times(other: Double) = DoubleFunction { a -> this(a) * other }
    operator fun div(other: Double) = DoubleFunction { a -> this(a) / other }
}

class VectorValuedFunction(function: (Double) -> DoubleVector): FunctionWrapper<Double, DoubleVector>(function) {
    operator fun plus(other: VectorValuedFunction) = VectorValuedFunction { a -> this(a) + other(a) }
    operator fun minus(other: VectorValuedFunction) = VectorValuedFunction { a -> this(a) - other(a) }
    operator fun times(other: VectorValuedFunction) = DoubleFunction { a -> this(a) * other(a) }
    infix fun cross(other: VectorValuedFunction) = VectorValuedFunction { a -> this(a).to3D cross other(a).to3D }
    operator fun div(other: VectorValuedFunction) = VectorValuedFunction { a -> this(a) / other(a) }

    operator fun plus(other: DoubleVector) = VectorValuedFunction { a -> this(a) + other }
    operator fun minus(other: DoubleVector) = VectorValuedFunction { a -> this(a) - other }
    operator fun times(other: DoubleVector) = DoubleFunction { a -> this(a) * other }
    infix fun cross(other: DoubleVector) = VectorValuedFunction { a -> this(a).to3D cross other.to3D }
    operator fun div(other: DoubleVector) = VectorValuedFunction { a -> this(a) / other }

    operator fun plus(other: Double) = VectorValuedFunction { a -> this(a) + other }
    operator fun minus(other: Double) = VectorValuedFunction { a -> this(a) - other }
    operator fun times(other: Double) = VectorValuedFunction { a -> this(a) * other }
    operator fun div(other: Double) = VectorValuedFunction { a -> this(a) / other }
}

class ScalarField(function: (DoubleVector) -> Double): FunctionWrapper<DoubleVector, Double>(function) {
    operator fun plus(other: ScalarField) = ScalarField { a -> this(a) + other(a) }
    operator fun minus(other: ScalarField) = ScalarField { a -> this(a) - other(a) }
    operator fun times(other: ScalarField) = ScalarField { a -> this(a) * other(a) }
    operator fun div(other: ScalarField) = ScalarField { a -> this(a) / other(a) }

    operator fun plus(other: DoubleVector) = VectorField { a -> other + this(a) }
    operator fun minus(other: DoubleVector) = VectorField { a -> other - this(a) }
    operator fun times(other: DoubleVector) = VectorField { a -> other * this(a) }
    operator fun div(other: DoubleVector) = VectorField { a -> other / this(a) }

    operator fun plus(other: Double) = ScalarField { a -> this(a) + other }
    operator fun minus(other: Double) = ScalarField { a -> this(a) - other }
    operator fun times(other: Double) = ScalarField { a -> this(a) * other }
    operator fun div(other: Double) = ScalarField { a -> this(a) / other }
}

class VectorField(function: (DoubleVector) -> DoubleVector): FunctionWrapper<DoubleVector, DoubleVector>(function) {
    operator fun plus(other: VectorField) = VectorField { a -> this(a) + other(a) }
    operator fun minus(other: VectorField) = VectorField { a -> this(a) - other(a) }
    operator fun times(other: VectorField) = ScalarField { a -> this(a) * other(a) }
    infix fun cross(other: VectorField) = VectorField { a -> this(a).to3D cross other(a).to3D }
    operator fun div(other: VectorField) = VectorField { a -> this(a) / other(a) }

    operator fun plus(other: ScalarField) = VectorField { a -> this(a) + other(a) }
    operator fun minus(other: ScalarField) = VectorField { a -> this(a) - other(a) }
    operator fun times(other: ScalarField) = VectorField { a -> this(a) * other(a) }
    operator fun div(other: ScalarField) = VectorField { a -> this(a) / other(a) }

    operator fun plus(other: DoubleVector) = VectorField { a -> this(a) + other }
    operator fun minus(other: DoubleVector) = VectorField { a -> this(a) - other }
    operator fun times(other: DoubleVector) = ScalarField { a -> this(a) * other }
    infix fun cross(other: DoubleVector) = VectorField { a -> this(a).to3D cross other.to3D }
    operator fun div(other: DoubleVector) = VectorField { a -> this(a) / other }

    operator fun plus(other: Double) = VectorField { a -> this(a) + other }
    operator fun minus(other: Double) = VectorField { a -> this(a) - other }
    operator fun times(other: Double) = VectorField { a -> this(a) * other }
    operator fun div(other: Double) = VectorField { a -> this(a) / other }
}