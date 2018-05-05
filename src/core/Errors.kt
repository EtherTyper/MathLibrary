package core

import kotlin.reflect.KClass

class ArityError(expected: Int, actual: Int) : Error("Expected vector of arity $expected but got $actual.")
class UnitVectorError(magnitude: Double) : Error("Expected unit vector but got vector of magnitude $magnitude.")
class OrthogonalityError : Error("Expected orthogonal vectors.")
class BadOperationError(a: Any, b: Any, kClass: KClass<*>) : Error("Cannot execute operation: $a ${kClass.simpleName} $b")

open class MatrixDimensionError(message: String) : Error(message)
class NotAMatrixError : MatrixDimensionError("Each row must be the same length.")
class SquareMatrixDimensionError : MatrixDimensionError("Square matrices must have the same number of columns as rows.")