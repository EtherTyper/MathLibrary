import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

class Operation<in A, in B, out C>(private val aType: KClass<*>, private val bType: KClass<*>, val function: Function2<A, B, C>) {
    fun compatibleParameters(aType: KClass<*>, bType: KClass<*>) =
            aType.isSubclassOf(this.aType) && bType.isSubclassOf(this.bType)
}

open class OperationList(private val operations: Array<Operation<*, *, *>>) {
    @Suppress("UNCHECKED_CAST")
    operator fun invoke(a: Any, b: Any): Any? {
        return (operations
                .single { operation -> operation.compatibleParameters(a::class, b::class) }
                .function as (Any, Any) -> Any)(a, b)
    }
}

object Multiply : OperationList(
        arrayOf(
                Operation(Double::class, Double::class) { a: Double, b: Double -> a * b },
                Operation(DoubleVector::class, Double::class) { a: DoubleVector, b: Double -> a * b },
                Operation(DoubleVector::class, DoubleVector::class) { a: DoubleVector, b: DoubleVector -> a * b },
                Operation(Derivative::class, DoubleFunction::class) { a: Derivative, b: DoubleFunction -> a * b },
                Operation(Derivative::class, Double::class) { a: Derivative, b: Double -> a * b },
                Operation(DirectionalDerivative::class, ScalarField::class) { a: DirectionalDerivative, b: ScalarField -> a * b },
                Operation(DirectionalDerivative::class, Double::class) { a: DirectionalDerivative, b: Double -> a * b },
                Operation(PartialDerivative::class, VectorField::class) { a: PartialDerivative, b: VectorField -> a * b },
                Operation(PartialDerivative::class, DoubleVector::class) { a: PartialDerivative, b: DoubleVector -> a * b }
        )
)

object Add : OperationList(
        arrayOf(
                Operation(Double::class, Double::class) { a: Double, b: Double -> a + b },
                Operation(DoubleVector::class, DoubleVector::class) { a: DoubleVector, b: DoubleVector -> a + b },
                Operation(DoubleVector::class, Double::class) { a: DoubleVector, b: Double -> a + b },
                Operation(Double::class, DoubleVector::class) { a: Double, b: DoubleVector -> b + a }
        )
)

object Subtract : OperationList(
        arrayOf(
                Operation(Double::class, Double::class) { a: Double, b: Double -> a - b },
                Operation(DoubleVector::class, DoubleVector::class) { a: DoubleVector, b: DoubleVector -> a - b },
                Operation(DoubleVector::class, Double::class) { a: DoubleVector, b: Double -> a - b },
                Operation(Double::class, DoubleVector::class) { a: Double, b: DoubleVector -> b - a }
        )
)