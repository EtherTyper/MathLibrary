package core.quantum

open class QuantumCircuit(val qubits: Int, val legs: MutableList<ParallelLeg>) {
    companion object {
        fun circuit(qubits: Int, init: QuantumCircuit.() -> Unit): QuantumCircuit {
            val circuit = QuantumCircuit(qubits, mutableListOf())
            circuit.init()

            return circuit
        }
    }

    class ParallelLeg(val qubits: Int, val gates: MutableList<GateApplication>) {
        fun applyGate(qubits: IntRange, gate: QuantumGate) {
            gates.add(GateApplication(qubits, gate))
        }

        // Evaulate quantum operations that run in parallel.
        val evaluate: QuantumGate get() {
            val usedGates: MutableSet<GateApplication> = mutableSetOf()
            var result = QuantumGate.identityGate(0)

            for (qubit in 0 until qubits) {
                var explicitGate: GateApplication? = null

                for (gateApplication in gates) {
                    // Find a gate which applies to this qubit.
                    if (gateApplication.qubits.start <= qubit && gateApplication.qubits.endInclusive >= qubit) {
                        explicitGate = gateApplication
                    }
                }

                // If we've already added this gate to the list, don't do that again.
                if (explicitGate != null && usedGates.add(explicitGate))
                    continue

                val gateToAdd = explicitGate?.gate ?: QuantumGate.identityGate(qubits)

                result = result combine gateToAdd
            }

            return result
        }
    }

    class GateApplication(val qubits: IntRange, val gate: QuantumGate)

    fun parallel(init: ParallelLeg.() -> Unit): ParallelLeg {
        val leg = ParallelLeg(qubits, mutableListOf())
        leg.init()

        return leg
    }
}