abstract class FunctionWrapper<in A, out B>(private val function: (A) -> B) {
    operator fun invoke(a: A): B = function(a)
}

class DoubleFunction(function: (Double) -> Double): FunctionWrapper<Double, Double>(function)
class VectorValuedFunction(function: (Double) -> DoubleVector): FunctionWrapper<Double, DoubleVector>(function)
class ScalarField(function: (DoubleVector) -> Double): FunctionWrapper<DoubleVector, Double>(function)
class VectorField(function: (DoubleVector) -> DoubleVector): FunctionWrapper<DoubleVector, DoubleVector>(function)