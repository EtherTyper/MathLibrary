import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

class Operation<in A, in B, out C>(private val aType: KClass<*>, private val bType: KClass<*>, val function: Function2<A, B, C>) {
    fun compatibleParameters(aType: KClass<*>, bType: KClass<*>) =
            aType.isSubclassOf(this.aType) && bType.isSubclassOf(this.bType)
}

open class OperationList(val operations: Array<Operation<*, *, *>>) {
    @Suppress("UNCHECKED_CAST")
    fun applyOperation(a: Any, b: Any): Any? {
        return (operations
                .single { operation -> operation.compatibleParameters(a::class, b::class) }
                .function as (Any, Any) -> Any)(a, b)
    }
}

object MultiplicationOperations : OperationList(
        arrayOf(
                Operation(Double::class, Double::class) { a: Double, b: Double -> a * b },
                Operation(DoubleVector::class, Double::class) { a: DoubleVector, b: Double -> a * b },
                Operation(DoubleVector::class, DoubleVector::class) { a: DoubleVector, b: DoubleVector -> a * b },
                Operation(Derivative::class, DoubleFunction::class) { a: Derivative, b: DoubleFunction -> a * b },
                Operation(Derivative::class, Double::class) { a: Derivative, b: Double -> a * b },
                Operation(DirectionalDerivative::class, ScalarField::class) { a: DirectionalDerivative, b: ScalarField -> a * b },
                Operation(DirectionalDerivative::class, Double::class) { a: Derivative, b: Double -> a * b },
                Operation(PartialDerivative::class, ScalarField::class) { a: PartialDerivative, b: VectorField -> a * b },
                Operation(PartialDerivative::class, DoubleVector::class) { a: PartialDerivative, b: DoubleVector -> a * b }
        )
)