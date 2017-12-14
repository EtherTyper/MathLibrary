import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

class Operation<in A, in B, out C>(private val aType: KClass<*>, private val bType: KClass<*>, val function: Function2<A, B, C>) {
    fun compatibleParameters(aType: KClass<*>, bType: KClass<*>) =
            aType.isSubclassOf(this.aType) && bType.isSubclassOf(this.bType)
}

open class OperationList(private val operations: Array<Operation<*, *, *>>) {
    @Suppress("UNCHECKED_CAST")
    operator fun invoke(a: Any, b: Any): Any? {
        try {
            return (operations
                    .single { operation -> operation.compatibleParameters(a::class, b::class) }
                    .function as (Any, Any) -> Any)(a, b)
        } catch (e: NoSuchElementException) {
            try {
                // We can reverse the order since the list doesn't include cross products,
                // so they should pretty much all be commutative.
                return (operations
                        .single { operation -> operation.compatibleParameters(b::class, a::class) }
                        .function as (Any, Any) -> Any)(b, a)
            } catch (e: NoSuchElementException) {
                print("Cannot execute operation: $a ${this::class} $b")

                return null
            }
        }
    }
}

object Multiply : OperationList(
        arrayOf(
                Operation(Double::class, Double::class) { a: Double, b: Double -> a * b },
                Operation(DoubleVector::class, Double::class) { a: DoubleVector, b: Double -> a * b },
                Operation(DoubleVector::class, DoubleVector::class) { a: DoubleVector, b: DoubleVector -> a * b },
                Operation(DoubleFunction::class, DoubleFunction::class) { a: DoubleFunction, b: DoubleFunction -> a * b },
                Operation(VectorValuedFunction::class, VectorValuedFunction::class) { a: VectorValuedFunction, b: VectorValuedFunction -> a * b },
                Operation(ScalarField::class, ScalarField::class) { a: ScalarField, b: ScalarField -> a * b },
                Operation(ScalarField::class, DoubleVector::class) { a: ScalarField, b: DoubleVector -> a + b },
                Operation(ScalarField::class, Double::class) { a: ScalarField, b: Double -> a + b },
                Operation(VectorField::class, VectorField::class) { a: VectorField, b: VectorField -> a * b },
                Operation(VectorField::class, VectorField::class) { a: VectorField, b: VectorField -> a * b },
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
                Operation(DoubleFunction::class, DoubleFunction::class) { a: DoubleFunction, b: DoubleFunction -> a + b },
                Operation(DoubleFunction::class, Double::class) { a: DoubleFunction, b: Double -> a + b },
                Operation(VectorValuedFunction::class, VectorValuedFunction::class) { a: VectorValuedFunction, b: VectorValuedFunction -> a + b },
                Operation(VectorValuedFunction::class, DoubleVector::class) { a: VectorValuedFunction, b: DoubleVector -> a + b },
                Operation(VectorValuedFunction::class, Double::class) { a: VectorValuedFunction, b: Double -> a + b },
                Operation(ScalarField::class, ScalarField::class) { a: ScalarField, b: ScalarField -> a + b },
                Operation(ScalarField::class, DoubleVector::class) { a: ScalarField, b: DoubleVector -> a + b },
                Operation(ScalarField::class, Double::class) { a: ScalarField, b: Double -> a + b },
                Operation(VectorField::class, ScalarField::class) { a: VectorField, b: VectorField -> a + b },
                Operation(VectorField::class, VectorField::class) { a: VectorField, b: VectorField -> a + b },
                Operation(VectorField::class, DoubleVector::class) { a: VectorField, b: DoubleVector -> a + b },
                Operation(VectorField::class, Double::class) { a: VectorField, b: Double -> a + b }
        )
)

object Subtract : OperationList(
        arrayOf(
                Operation(Double::class, Double::class) { a: Double, b: Double -> a - b },
                Operation(DoubleVector::class, DoubleVector::class) { a: DoubleVector, b: DoubleVector -> a - b },
                Operation(DoubleVector::class, Double::class) { a: DoubleVector, b: Double -> a - b },
                Operation(DoubleFunction::class, DoubleFunction::class) { a: DoubleFunction, b: DoubleFunction -> a - b },
                Operation(DoubleFunction::class, Double::class) { a: DoubleFunction, b: Double -> a - b },
                Operation(VectorValuedFunction::class, VectorValuedFunction::class) { a: VectorValuedFunction, b: VectorValuedFunction -> a - b },
                Operation(VectorValuedFunction::class, DoubleVector::class) { a: VectorValuedFunction, b: DoubleVector -> a - b },
                Operation(VectorValuedFunction::class, Double::class) { a: VectorValuedFunction, b: Double -> a - b },
                Operation(ScalarField::class, ScalarField::class) { a: ScalarField, b: ScalarField -> a - b },
                Operation(ScalarField::class, DoubleVector::class) { a: ScalarField, b: DoubleVector -> a - b },
                Operation(ScalarField::class, Double::class) { a: ScalarField, b: Double -> a - b },
                Operation(VectorField::class, ScalarField::class) { a: VectorField, b: VectorField -> a - b },
                Operation(VectorField::class, VectorField::class) { a: VectorField, b: VectorField -> a - b },
                Operation(VectorField::class, DoubleVector::class) { a: VectorField, b: DoubleVector -> a - b },
                Operation(VectorField::class, Double::class) { a: VectorField, b: Double -> a - b }
        )
)