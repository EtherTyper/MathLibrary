package core.complex

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import com.beust.klaxon.json
import java.nio.charset.StandardCharsets
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.sqrt

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
    public val argument get(): Double = atan2(imaginary, real)

    // Used in converting complex vectors to real vectors.
    val mostlyPositive get(): Boolean = argument < PI / 2 || argument > 3 * PI / 2
    val collapseToReal get(): Double = magnitude * if (mostlyPositive) 1 else -1

    // Number conformance
    override fun toByte() = magnitude.toByte()

    override fun toChar() = magnitude.toChar()
    override fun toDouble() = magnitude
    override fun toFloat() = magnitude.toFloat()
    override fun toInt() = magnitude.toInt()
    override fun toLong() = magnitude.toLong()
    override fun toShort() = magnitude.toShort()

    override fun toString(): String {
        return "%.3f + %.3fi".format(real, imaginary)
    }

    val toJSON
        get() = json {
            obj(
                    "real" to real,
                    "imaginary" to imaginary
            )
        }

    @Suppress("ObjectPropertyName")
    companion object {
        val `0` = Complex(0.0, 0.0)
        val i = Complex(0.0, 1.0)

        fun fromJSON(jsonObject: JsonObject): Complex {
            return Complex((jsonObject["real"] as Number).toDouble(), (jsonObject["imaginary"] as Number).toDouble())
        }
    }
}

fun String.toComplex(): Complex {
    val (realString, imaginaryString) = this.replace(Regex("\\s+"), "").split("+")

    val real = realString.toDouble()
    val imaginary = imaginaryString.filterNot { c -> c == 'i' }.toDouble()

    return Complex(real, imaginary)
}

operator fun Double.plus(other: Complex) = other + this
operator fun Double.minus(other: Complex) = Complex(this) - other
operator fun Double.times(other: Complex) = other * this
operator fun Double.div(other: Complex) = Complex(this) / other