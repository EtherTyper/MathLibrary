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

    section("Vector Operations") {
        val vector1 = DoubleVector(2.0, 3.0, 4.0)
        val vector2 = DoubleVector(5.0, 6.0, 7.0)

        println("$vector1 + $vector2 = " + (vector1 + vector2))
        println("$vector1 - $vector2 = " + (vector1 - vector2))
        println("$vector1 ⋅ $vector2 = " + (vector1 * vector2))
        println("$vector1 × $vector2 = " + (vector1.to3D cross vector2.to3D))
        println("‖$vector1‖ = " + vector1.magnitude)
    }

    section("Derivatives") {
        val doubleFunction = DoubleFunction { i: Double -> Math.pow(i, 2.0) * 5 }
        val scalarField = ScalarField { vector: DoubleVector -> Math.pow(vector[0], 2.0) * vector[1] }
        val vectorField = VectorField { vector: DoubleVector -> vector.to3D cross DoubleVector.i }

        println("(i ^ 2 * 5)'|(i=3) = " + doubleFunction.differentiate(0.0001, 3.0))
        println("∇(x ^ 2 * y)|(3, 2) = " + scalarField.gradient(DoubleVector(3.0, 2.0)))
        println("δ(\u0305v × i\u0302)/δy = " + vectorField.partialDerivative(1, DoubleVector(2.0, 3.0, 4.0)))

        println((Derivative * DoubleFunction { i: Double -> i + 0.2 })(1.0))
    }

    section("Matrices") {
        println(SquareMatrix(arrayOf(arrayOf(1.0, 3.0), arrayOf(1.0, 4.0))).determinant)

        println(SquareMatrix(arrayOf(arrayOf(0.0, 1.0, 2.0), arrayOf(3.0, 4.0, 5.0), arrayOf(6.0, 7.0, 8.0))).determinant)
    }
}