package core.quantum

fun QFT(qubits: Int) = QuantumCircuit.circuit(qubits, {
    for (i in 0 until qubits) {
        applyGate(listOf(i), QuantumGate.H)

        for (j in i + 1 until qubits) {
            applyGate(listOf(j, i), QuantumGate.controlled(
                    QuantumGate.R(
                            (j - i + 1).toDouble()
                    )
            ))
        }
    }
}).evaluate