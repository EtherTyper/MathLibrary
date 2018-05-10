package tests

public class AllTests: Tests() {
    companion object {
        @JvmStatic
        fun main(vararg args: String) {
            VectorTests.main()
            MatrixTests.main()
            ComplexTests.main()
            QuantumTests.main()
        }
    }
}