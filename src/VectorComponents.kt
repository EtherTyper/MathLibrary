import java.util.*

// Scalar fields.
operator fun ScalarField.get(direction: Int, parameter: DoubleVector): DoubleFunction {
    return DoubleFunction { value: Double ->
        val newDimensions = Arrays.copyOf(parameter.dimensions, parameter.arity)
        newDimensions[direction] = value

        this(DoubleVector(*newDimensions))
    }
}

// Vector fields.
operator fun VectorField.get(direction: Int, parameter: DoubleVector): VectorValuedFunction {
    return VectorValuedFunction { value: Double ->
        val newDimensions = Arrays.copyOf(parameter.dimensions, parameter.arity)
        newDimensions[direction] = value

        this(DoubleVector(*newDimensions))
    }
}