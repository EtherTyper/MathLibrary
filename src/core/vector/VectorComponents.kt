package core.vector

operator fun ScalarField.get(direction: Int, parameter: DoubleVector): DoubleFunction {
    return DoubleFunction { value: Double ->
        val newDimensions = parameter.dimensions.copyOf()
        newDimensions[direction] = value

        this(DoubleVector(*newDimensions))
    }
}

operator fun VectorField.get(direction: Int, parameter: DoubleVector): VectorValuedFunction {
    return VectorValuedFunction { value: Double ->
        val newDimensions = parameter.dimensions.copyOf()
        newDimensions[direction] = value

        this(DoubleVector(*newDimensions))
    }
}