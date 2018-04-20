package core.complex
import kotlin.math.*

class Complex(public val real: Double, public val imaginary: Double = 0.0) : Number() {
    operator fun plus(other: Complex) = Complex(
            this.real + other.real,
            this.imaginary + other.imaginary
    )

    operator fun plus(other: Double) = this + Complex(other)

    operator fun minus(other: Complex) = this + -1.0 * other
    operator fun minus(other: Double) = this - Complex(other)

    operator fun times(other: Complex) = Complex(
            this.real * other.real - this.imaginary * other.imaginary,
            this.real * other.imaginary + this.imaginary * other.real
    )

    operator fun times(other: Double) = this * Complex(other)

    operator fun div(other: Complex): Complex {
        val numerator = this * other.conjugate
        val denominator = (other * other.conjugate).real // Should always be real.

        return numerator / denominator
    }

    operator fun div(other: Double) = this * (1 / other)

    operator fun unaryMinus() = this * -1.0

    public val conjugate get(): Complex = Complex(real, -imaginary)
    public val magnitude get(): Double = sqrt((this * conjugate).real)
    public val angle get(): Double = atan2(imaginary, real)

    // Used in converting complex vectors to real vectors.
    val mostlyPositive get(): Boolean = angle < PI / 2 || angle > 3 * PI / 2
    val collapseToReal get(): Double = magnitude * if (mostlyPositive) 1 else -1

    // Number conformance
    override fun toByte() = magnitude.toByte()
    override fun toChar() = magnitude.toChar()
    override fun toDouble() = magnitude
    override fun toFloat() = magnitude.toFloat()
    override fun toInt() = magnitude.toInt()
    override fun toLong() = magnitude.toLong()
    override fun toShort() = magnitude.toShort()

    @Suppress("ObjectPropertyName")
    companion object {
        val `0` = Complex(0.0, 0.0)
        val i = Complex(0.0, 1.0)
    }
}

operator fun Double.plus(other: Complex) = other + this
operator fun Double.minus(other: Complex) = Complex(this) - other
operator fun Double.times(other: Complex) = other * this
operator fun Double.div(other: Complex) = Complex(this) / other