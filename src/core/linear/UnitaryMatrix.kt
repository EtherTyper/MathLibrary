package core.linear

import core.OrthogonalityError
import core.UnitVectorError
import core.vector.defaultDelta
import kotlin.math.abs

open class UnitaryMatrix(members: Array<Array<out Any>>, delta: Double = defaultDelta) : SquareMatrix(members) {
    init {
        for (i in 0.until(cols)) {
            // All columns must be unit vectors.
            if (abs(column[i].vector.magnitude - 1) >= delta)
                throw UnitVectorError(column[i].vector.magnitude)

            // All vectors must be orthogonal to each other.
            for (j in (i + 1).until(cols)) {
                if ((column[i].vector * column[j].vector).magnitude >= delta)
                    throw OrthogonalityError()
            }
        }
    }

    operator fun times(other: UnitaryMatrix) = UnitaryMatrix(super.times(other).members)
}