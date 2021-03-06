package core.transform

import core.differential.Derivative
import core.vector.DoubleFunction
import kotlin.math.pow

fun Int.factorial(): Int {
    return if (this <= 0) {
        1
    } else {
        this * (this - 1).factorial()
    }
}

fun DoubleFunction.nthDerivative(n: Int): DoubleFunction {
    return if (n <= 0) {
        this
    } else {
        Derivative * this.nthDerivative(n - 1)
    }
}

fun taylorSeriesTerm(term: Int, function: DoubleFunction, center: Int): DoubleFunction {
    return function.nthDerivative(term) / term.factorial().toDouble() * DoubleFunction({ x ->
        (x - center).pow(term)
    })
}

open class TaylorSeries(function: DoubleFunction, accuracy: Int, center: Int) : Series(
        { term -> taylorSeriesTerm(term, function, center) }, accuracy
)

open class MaclaurinSeries(function: DoubleFunction, accuracy: Int) : TaylorSeries(function, accuracy, 0)