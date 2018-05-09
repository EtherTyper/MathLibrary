package applications.quantum

import core.InvalidRangeError

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
            if (qubits.count() != gate.qubits) {
                throw InvalidRangeError(gate.qubits, qubits.count())
            }

            gates.add(GateApplication(qubits, gate))
        }

        // Evaluate quantum operations that run in parallel.
        //
        // This is done through repeated tensor products,
        // and filling in I matrices for every qubit that's
        // not being operated on.
        val evaluate: QuantumGate
            get() {
                val newQubitOrder = mutableListOf<Int>()
                var reorderedGate = QuantumGate.identity(0)

                for (gateApplication in gates) {
                    for (qubit in gateApplication.qubits) {
                        newQubitOrder.add(qubit)
                    }

                    reorderedGate = reorderedGate combine gateApplication.gate
                }

                // Fill in all the unused qubits with identity operations.
                newQubitOrder.addAll((0 until qubits).filter({ qubit -> !newQubitOrder.contains(qubit) }))
                reorderedGate = reorderedGate combine QuantumGate.identity(qubits - reorderedGate.qubits)

                val swapGateSequence = qubitCommutationGate(newQubitOrder)
                val inverseSwapGateSequence = QuantumGate(qubits, swapGateSequence.transpose.members)

                return inverseSwapGateSequence * reorderedGate * swapGateSequence
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
                QuantumGate.identity(qubits),
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