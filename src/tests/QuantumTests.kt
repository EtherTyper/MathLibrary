package tests

import applications.quantum.*
import core.complex.Complex
import kotlin.math.sqrt

class QuantumTests : Tests() {
    companion object {
        @JvmStatic
        fun main(vararg args: String) {
            section("Quantum States and Rearrangements") {
                val eyeBasis = QuantumBasis.eyeBasis(3)
                val basisStates = (0 until eyeBasis.qubits.binaryExp).map { i -> eyeBasis.states[i] }

                println("Basis states of 3 qubits in order:\n${basisStates.joinToString("\n")}\n")

                val newOrder = mutableListOf(4, 2, 3, 1)

                println("Qubit rearrangement from ${newOrder.sorted()} -> $newOrder:")

                val qubitCommutationGate = qubitCommutationGate(newOrder)

                for (i in 0 until qubitCommutationGate.qubits) {
                    val initialState = QuantumBasis.eyeBasis(qubitCommutationGate.qubits).states[i.binaryExp]
                    val resultingState = qubitCommutationGate * initialState

                    println("$initialState -> $resultingState")
                }


            }

            section("Quantum Gates") {
                // Gates!
                println("H = \n${QuantumGate.H}\n")
                println("√X = \n${QuantumGate.sqrtNOT}\n")
                println("X = \n${QuantumGate.X}\n")
                println("Y = \n${QuantumGate.Y}\n")
                println("Z = \n${QuantumGate.Z}\n")
                println("√S = \n${QuantumGate.sqrtS}\n")
                println("S = \n${QuantumGate.S}\n")
                println("cX = \n${QuantumGate.cX}\n")
                println("cY = \n${QuantumGate.cY}\n")
                println("cZ = \n${QuantumGate.cZ}\n")
                println("CCNOT = \n${QuantumGate.CCNOT}\n")
                println("cS = \n${QuantumGate.cS}\n")
            }

            section("Quantum Computer Simulator") {
                val circuit = QuantumCircuit.circuit(3) {
                    // Hadamard the first qubit.
                    applyGate(0..0, QuantumGate.H)

                    parallel {
                        // Swap all data in the first and third qubits.
                        // At the same time, invert the second qubit.
                        applyGate(listOf(0, 2), QuantumGate.S)
                        applyGate(1..1, QuantumGate.X)
                    }

                    // Undo the Hadamard operation on the third qubit by applying it again.
                    applyGate(2..2, QuantumGate.H)
                }

                println("Equivalent gate for first step of circuit: \n${circuit.parallelLegs[0].evaluate}\n")
                println("Equivalent gate for entire circuit: \n${circuit.evaluate}\n")

                val input = (QuantumGate.identity(2) combine QuantumGate.H) * QuantumBasis.eyeBasis(3).states[6]

                println("Input state: \n$input\n")

                val output = circuit apply input

                println("Input run through the circuit: \n$output\n")
                println("Input run through the circuit and measured: \n${output.measure()}")
            }

            section("Quantum Fourier Transform") {
                println("QFT(2) = \n${QFT(2)}")
            }

            section("Bloch Vectors") {
                val states = listOf(
                        // North Pole, |0>
                        QuantumState(1, Complex.`0`, Complex(1.0)),
                        QuantumState(1, Complex.`0`, Complex.i),

                        // South Pole, |1>
                        QuantumState(1, Complex(1.0), Complex.`0`),
                        QuantumState(1, Complex.i, Complex.`0`),

                        // States along the equator.
                        QuantumState(1, Complex(1.0) / sqrt(2.0), Complex(1.0) / sqrt(2.0)),
                        QuantumState(1, Complex(1.0) / sqrt(2.0), Complex.i / sqrt(2.0)),
                        QuantumState(1, Complex(1.0) / sqrt(2.0), Complex(-1.0) / sqrt(2.0)),
                        QuantumState(1, Complex(1.0) / sqrt(2.0), -Complex.i / sqrt(2.0))
                )

                for (state in states) {
                    println("$state -> ${BlochVector.from(state)}")
                }
            }
        }
    }
}