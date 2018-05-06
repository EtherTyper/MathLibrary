package core.quantum

@QuantumCircuit.CircuitMarker
open class QuantumCircuit(val qubits: Int, val parallelLegs: MutableList<ParallelLeg>) {
    // So people can't call "parallel" from within the context of a parallel leg builder.
    // See https://kotlinlang.org/docs/reference/type-safe-builders.html#full-definition-of-the-comexamplehtml-package.
    @DslMarker
    annotation class CircuitMarker

    class GateApplication(val qubits: Iterable<Int>, val gate: QuantumGate)

    @CircuitMarker
    class ParallelLeg(val qubits: Int, val gates: MutableList<GateApplication>) {
        fun applyGate(qubits: Iterable<Int>, gate: QuantumGate) {
            gates.add(GateApplication(qubits, gate))
        }

        // Evaluate quantum operations that run in parallel.
        //
        // This is done through repeated tensor products,
        // and filling in I matrices for every qubit that's
        // not being operated on.
        val evaluate: QuantumGate
            get() {
                val usedGates: MutableSet<GateApplication> = mutableSetOf()
                var result = QuantumGate.identityGate(0)

                for (qubit in qubits - 1 downTo 0) {
                    var explicitGate: GateApplication? = null

                    for (gateApplication in gates) {
                        // Find a gate which applies to this qubit.
                        if (gateApplication.qubits.contains(qubit)) {
                            explicitGate = gateApplication
                        }
                    }

                    // If we've already added this gate to the list, don't do that again.
                    if (explicitGate != null && !usedGates.add(explicitGate))
                        continue

                    // If no gate is applied to this qubit, we can think of an identity
                    // gate being applied to it instead, implicitly.
                    val gateToAdd = explicitGate?.gate ?: QuantumGate.identityGate(1)

                    result = result combine gateToAdd
                }

                return result
            }
    }

    fun applyGate(qubits: Iterable<Int>, gate: QuantumGate) {
        parallel {
            applyGate(qubits, gate)
        }
    }

    // Evaluate quantum operations that run in sequence.
    //
    // This is done through repeated matrix multiplication.
    // The latest matrices are applied the furthest left (last.)
    val evaluate
        get() = parallelLegs.foldRight(
                QuantumGate.identityGate(qubits),
                { leg, acc -> leg.evaluate * acc }
        )

    companion object {
        fun circuit(qubits: Int, init: QuantumCircuit.() -> Unit): QuantumCircuit {
            val circuit = QuantumCircuit(qubits, mutableListOf())
            circuit.init()

            return circuit
        }
    }

    fun parallel(init: ParallelLeg.() -> Unit) {
        val leg = ParallelLeg(qubits, mutableListOf())
        leg.init()

        parallelLegs.add(leg)
    }

    infix fun apply(other: QuantumState) = evaluate * other
}