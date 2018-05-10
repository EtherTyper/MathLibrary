package tests

import core.complex.*

class ComplexTests: Tests() {
    companion object {
        @JvmStatic
        fun main(vararg args: String) {
            section("Complex Arithmetic and Special Functions") {
                val complexNumber = Complex(1.0, 1.0)

                println("x = $complexNumber")
                println("\u0305x = ${complexNumber.conjugate}")
                println("x^2 = ${complexNumber * complexNumber}")
                println("|x| = ${complexNumber.magnitude}")
                println()
                println("sin(x) = ${sin(complexNumber)}")
                println("cos(x) = ${cos(complexNumber)}")
                println("tan(x) = ${tan(complexNumber)}")
                println()
                println("csc(x) = ${csc(complexNumber)}")
                println("sec(x) = ${sec(complexNumber)}")
                println("cot(x) = ${cot(complexNumber)}")
                println()
                println("sqrt(x) = ${sqrt(complexNumber)}")
                println()
                println("asin(x) = ${asin(complexNumber)}")
                println("acos(x) = ${acos(complexNumber)}")
                println("atan(x) = ${atan(complexNumber)}")
                println()
                println("ln(x) = ${ln(complexNumber)}")
                println("e^x = ${exp(complexNumber)}")
            }
        }
    }
}