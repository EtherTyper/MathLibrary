fun main(args: Array<String>) {
    println("i\u0302: ${Vector.i}")
    println("j\u0302: ${Vector.j}")
    println("k\u0302: ${Vector.k}")

    println(Vector(2.0, 3.0, 4.0).to3D cross Vector(5.0, 6.0, 7.0).to3D)

    println(
            VectorField({ vector -> vector.to3D cross Vector(1.0, 0.0, 0.0).to3D })(
                    Vector(2.0, 3.0, 4.0)
            )
    )
}