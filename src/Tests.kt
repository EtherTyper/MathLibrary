fun main(args: Array<String>) {
    println("Constants")
    println()
    println("i\u0302: ${Vector.i}")
    println("j\u0302: ${Vector.j}")
    println("k\u0302: ${Vector.k}")

    println(123.456.v)

    try {
        println("This should error, as 3D vectors should have dimensionalities of exactly 3.")
        println(123.456.v.to3D)
    } catch (e: Error) {
        println("Error: ${e.message}")
    }

    println(Vector(2.0, 3.0, 4.0).to3D cross Vector(5.0, 6.0, 7.0).to3D)
    println(Vector(2.0, 3.0, 4.0) * Vector(5.0, 6.0, 7.0))
    println(Vector(1.0, 1.0).magnitude)

    println(
            { vector: Vector -> vector.to3D cross Vector.i }(
                    Vector(2.0, 3.0, 4.0)
            )
    )

    println({ i: Double -> Math.pow(i, 2.0) * 5}.differentiate(0.0001, 3.0))

    println(
            { vector: Vector -> Math.pow(vector[0], 2.0) * vector[1]}.gradient(
                    Vector(3.0, 2.0),
                    0.0001
            )
    )

    println((Derivative * { i: Double -> i + 0.2 })(1.0))

    println("MATRIX")
    println(SquareMatrix(arrayOf(arrayOf(1.0, 3.0), arrayOf(1.0, 4.0))).determinant)

    println(SquareMatrix(arrayOf(arrayOf(0.0, 1.0, 2.0), arrayOf(3.0, 4.0, 5.0), arrayOf(6.0, 7.0, 8.0))).determinant)
}