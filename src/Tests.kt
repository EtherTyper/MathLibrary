fun section(name: String, block: (() -> Unit)) {
    print("\u001B[1;31m\n$name\n\n\u001B[0m")
    block()
}

fun main(args: Array<String>) {
    section("Constants") {
        println("i\u0302: ${DoubleVector.i}")
        println("j\u0302: ${DoubleVector.j}")
        println("k\u0302: ${DoubleVector.k}")

        println("Constant: ${123.456.v}")
    }
    
    section("Arity Checks") {
        try {
            println("This should error, as 3D vectors should have arities of exactly 3.")
            println(123.456.v.to3D)
        } catch (e: Error) {
            println("Error: ${e.message}")
            println("\u001B[1;32m\u2705 Success!\u001B[0m")
        }
    }

    val vector1 = DoubleVector(2.0, 3.0, 4.0)
    val vector2 = DoubleVector(5.0, 6.0, 7.0)

    section("Vector Operations") {

        println("$vector1 + $vector2 = " + (vector1 + vector2))
        println("$vector1 - $vector2 = " + (vector1 - vector2))
        println("$vector1 ⋅ $vector2 = " + (vector1 * vector2))
        println("$vector1 × $vector2 = " + (vector1.to3D cross vector2.to3D))
        println("‖$vector1‖ = " + vector1.magnitude)
    }

    val doubleFunction = DoubleFunction { i: Double -> Math.pow(i, 2.0) * 5 }
    val scalarField = ScalarField { vector: DoubleVector -> Math.pow(vector[0], 2.0) * vector[1] }
    val rotatingVectorField = VectorField { vector: DoubleVector -> vector.to3D cross DoubleVector.i }

    val outwardsField = VectorField { vector: DoubleVector -> vector * vector.magnitude }

    section("Derivatives") {
        println("(i ^ 2 * 5)'|(i=3) = " + doubleFunction.differentiate(3.0))
        println("∇(x ^ 2 * y)|(3, 2) = " + scalarField.gradient(DoubleVector(3.0, 2.0)))
        println("δ(\u0305v × i\u0302)/δy|(2, 3, 4) = " + rotatingVectorField.partialDerivative(1, vector1))
        println()
        println("d/di (i ^ 2 * 5)|(i=3) = " + (Derivative * doubleFunction)(3.0))
        println("δ/δx (x ^ 2 * y)|(3, 2) = " + (DirectionalDerivative.d_dx * scalarField)(DoubleVector(3.0, 2.0)))
        println("δ/δy (\u0305v × i\u0302)|(2, 3, 4) = " + (DirectionalDerivative.d_dy * rotatingVectorField)(DoubleVector.`0`))
    }

    section("Nabla Operations") {
        println("∇(x ^ 2 * y)|(3, 2) = " + Nabla(scalarField)(DoubleVector(3.0, 2.0)))
        println("∇ ⋅ (‖\u0305v‖ * \u0305v)|(2, 3, 4) = " + (Nabla * outwardsField)(vector1))
    }

    section("Matrices") {
        println(SquareMatrix(arrayOf(arrayOf(1.0, 3.0), arrayOf(1.0, 4.0))).determinant)

        println(SquareMatrix(arrayOf(arrayOf(0.0, 1.0, 2.0), arrayOf(3.0, 4.0, 5.0), arrayOf(6.0, 7.0, 8.0))).determinant)
    }
}