package core.complex
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.sin

class Complex(public val real: Double, public val imaginary: Double = 0.0) /* : Number() */ {
    operator fun plus(other: Complex) = Complex(
            this.real + other.real,
            this.imaginary + other.imaginary
    )

    operator fun plus(other: Double) = this + Complex(other)

    operator fun times(other: Complex) = Complex(
            this.real * other.real - this.imaginary * other.imaginary,
            this.real * other.imaginary + this.imaginary * other.real
    )

    operator fun times(other: Double) = this * Complex(other)

    val conjugate get(): Complex = Complex(real, -imaginary)
    val absoluteValue get(): Double = (this * conjugate).real
}

fun exp(i: Complex): Complex = exp(i.real) * Complex(cos(i.imaginary), sin(i.imaginary))

operator fun Double.plus(other: Complex) = other + this
operator fun Double.times(other: Complex) = other * this