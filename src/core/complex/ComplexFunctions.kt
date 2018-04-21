package core.complex

import kotlin.math.PI
import kotlin.math.cosh
import kotlin.math.sinh

fun sin(n: Complex): Complex = Complex(
        kotlin.math.sin(n.real) * cosh(n.imaginary),
        kotlin.math.cos(n.real) * sinh(n.imaginary)
)

fun cos(x: Complex): Complex = Complex(
        kotlin.math.cos(x.real) * cosh(x.imaginary),
        -kotlin.math.sin(x.real) * sinh(x.imaginary)
)

fun tan(n: Complex): Complex = sin(n) / cos(n)
fun csc(n: Complex): Complex = Complex(1.0) / sin(n)
fun sec(n: Complex): Complex = Complex(1.0) / cos(n)
fun cot(n: Complex): Complex = Complex(1.0) / tan(n)

fun sqrt(n: Complex): Complex = exp(0.5 * ln(n))

fun asin(n: Complex): Complex = -Complex.i * ln(Complex.i + sqrt(1.0 - n * n))
fun acos(n: Complex): Complex = PI - asin(n)
fun atan(n: Complex): Complex = 0.5 * Complex.i * (ln(1.0 - Complex.i * n) + ln(1.0 + Complex.i * n))

fun ln(n: Complex): Complex = Complex(kotlin.math.ln(n.magnitude), n.angle)

fun exp(n: Complex): Complex = kotlin.math.exp(n.real) * Complex(kotlin.math.cos(n.imaginary), kotlin.math.sin(n.imaginary))
