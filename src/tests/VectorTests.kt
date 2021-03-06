package tests

import applications.mechanics.Mass
import applications.mechanics.Projectile
import core.differential.*
import core.vector.*
import kotlin.math.*

class VectorTests : Tests() {
    companion object {
        @JvmStatic
        fun main(vararg args: String) {
            section("Constants") {
                println("i\u0302: ${DoubleVector.i}")
                println("j\u0302: ${DoubleVector.j}")
                println("k\u0302: ${DoubleVector.k}")

                println("Constant: ${1.v}")
            }

            section("Arity Checks") {
                println("This should error, as 3D vectors should have arities of exactly 3.")
                shouldError { 1.v.to3D }
            }

            val vector1 = DoubleVector(2.0, 3.0, 4.0)
            val vector2 = DoubleVector(5.0, 6.0, 7.0)

            section("Vector Operations") {
                println("$vector1 + $vector2 = ${vector1 + vector2}")
                println("$vector1 - $vector2 = ${vector1 - vector2}")
                println("$vector1 ⋅ $vector2 = ${vector1 * vector2}")
                println("$vector1 × $vector2 = ${vector1.to3D cross vector2.to3D}")
                println("$vector1 = \n${vector1.column}")
                println("$vector1' = ${vector1.row}")
                println("$vector1 ⊗ $vector2 = \n${vector1 outer vector2}")
                println("‖$vector1‖ = ${vector1.magnitude}")
                println("$vector1\u0302 = ${vector1.unit}")
                println("proj $vector1($vector2) = ${vector1 projectionOnto vector2}")
                println("rej $vector1($vector2) = ${vector1 rejectionFrom vector2}")
                println("Angle between $vector1 and $vector2: ${vector1 angleFrom vector2} radians")
            }

            val doubleFunction = DoubleFunction { i: Double -> i.pow(2.0) * 5 }
            val scalarField = ScalarField { vector: DoubleVector -> vector[0].pow(2.0) * vector[1] }
            val rotatingVectorField = VectorField { vector: DoubleVector -> vector.to3D cross DoubleVector.i }

            val outwardsField = VectorField { vector: DoubleVector -> vector * vector.magnitude }

            section("Derivatives") {
                println("(i ^ 2 * 5)'|(i=3) = ${doubleFunction.differentiate(3.0)}")
                println("∇(x ^ 2 * y)|(3, 2) = ${scalarField.gradient(DoubleVector(3.0, 2.0))}")
                println("δ(\u0305v × i\u0302)/δy|(2, 3, 4) = ${rotatingVectorField.partialDerivative(1, vector1)}")
                println()
                println("d/di (i ^ 2 * 5)|(i=3) = ${(Derivative * doubleFunction)(3.0)}")
                println("δ/δx (x ^ 2 * y)|(3, 2) = ${(DirectionalDerivative.d_dx * scalarField)(DoubleVector(3.0, 2.0))}")
                println("δ/δy (\u0305v × i\u0302)|(0, 0, 0) = ${(DirectionalDerivative.d_dy * rotatingVectorField)(DoubleVector.`0`)}")
                println()
                println("d/dt 1 = ${Derivative * 1.0}")
                println("δ/δx 1 = ${DirectionalDerivative.d_dx * 1.0}")
                println("δ/δx i\u0302 = ${DirectionalDerivative.d_dx * DoubleVector.i}")
            }

            section("Nabla Operations") {
                println("∇(x ^ 2 * y)|(3, 2) = ${Nabla(scalarField)(DoubleVector(3.0, 2.0))}")
                println("∇ ⋅ (‖\u0305v‖ * \u0305v)|(2, 3, 4) = ${(Nabla * outwardsField)(vector1)}")
                println("∇ × (\u0305v × i\u0302)|(2, 3, 4) = ${(Nabla cross rotatingVectorField)(vector1)}")
                println("∇<4.0, 3.0>(x ^ 2 * y)|(3, 2) = ${(Nabla[DoubleVector(4.0, 3.0)] * scalarField)(DoubleVector(3.0, 2.0))}")
            }

            val circle = VectorValuedFunction { t -> DoubleVector(4 * cos(2 * t), 4 * sin(2 * t)) }

            section("VVF Operations") {
                println("For the circle \u0305r = 4cos(2t)i\u0302 + 4sin(2t)j\u0302 at (0,0):")
                println()
                println("Unit tangent = ${circle.unitTangent(0.0)}")
                println("Principal unit normal = ${circle.principalUnitNormal(0.0)}")
                println("Curvature = ${circle.curvature(0.0)}")
            }

            section("Type-Agnostic Operations") {
                println("<1, 2, 3> * 2.5 = ${Multiply(DoubleVector(1.0, 2.0, 3.0), 2.5)}")

                val derivativeOfVectorField = Multiply(DirectionalDerivative.d_dy, rotatingVectorField) as VectorField
                println("δ/δy (\u0305v × i\u0302)|(2, 3, 4) = ${derivativeOfVectorField(DoubleVector.`0`)}")
                println()

                println("This should error, as scalars and vectors cannot be added.")
                shouldError { Add(DoubleVector(1.0, 2.0, 3.0), 2.5) }
            }

            val projectile = Projectile(10.0, 10.0, PI / 4)
            val mass = Mass { position -> ln(position.magnitude) }

            section("Other Applications (and cool Physics!)") {
                println(projectile)
                println()
                println("Projectile position at 3s: ${projectile(3.0)} m")
                println("Projectile mass at 3s: ${mass(projectile)(3.0)} kg")
                println("Projectile density at 3s: ${mass.density(projectile)(3.0)}")
                println("Force gravity on projectile at 3s: ${(projectile gravityOn mass)(3.0)} N")
                println("Torque gravity about (0, 1, 0) on projectile at 3s: " +
                        "${(projectile gravityOn mass).torqueAbout(DoubleVector(0.0, 1.0, 0.0).to3D)(3.0)} Nm")
            }
        }
    }
}