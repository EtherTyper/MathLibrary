package core.transform

import core.vector.DoubleFunction

open class Series(term: (Int) -> DoubleFunction, terms: Int) : DoubleFunction(
        (0 until terms).map(term).reduce({ term1, term2 -> term1 + term2 })::invoke
)