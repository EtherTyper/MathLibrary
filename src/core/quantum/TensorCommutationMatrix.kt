package core.quantum

fun qubitCommutationMatrix(order: IntArray): QuantumGate {
    var gate = QuantumGate.identity(order.size)

    for (i in order.indices) {
        var minimumIndex = i

        for (j in i until order.size) {
            if (order[j] <= order[i]) {
                minimumIndex = j
            }
        }

        // Now move the minimum value down and shift the rest up.
        order[i] = order[minimumIndex]
        for (j in minimumIndex downTo i + 1) {
            order[j] = order[j - 1]
        }

        // Simulate this movement with the
        gate = qubitSwapMatrix(order.size, i, minimumIndex) * gate
    }

    return gate
}

fun qubitSwapMatrix(qubits: Int, firstQubit: Int, secondQubit: Int): QuantumGate {
    var gate = QuantumGate.identity(qubits)

    for (i in secondQubit downTo firstQubit + 1) {
        val totalSwapGate = QuantumGate.identity((i - 1) - 0) combine
                QuantumGate.S combine QuantumGate.identity((qubits - 1) - i)

        gate = totalSwapGate * gate
    }

    return gate
}

fun main(args: Array<String>) {
    val swapper = qubitCommutationMatrix(intArrayOf(3, 1, 2, 0))

    println(swapper * QuantumBasis.eyeBasis(4).states[1])
    println(swapper * QuantumBasis.eyeBasis(4).states[2])
    println(swapper * QuantumBasis.eyeBasis(4).states[4])
    println(swapper * QuantumBasis.eyeBasis(4).states[8])
}