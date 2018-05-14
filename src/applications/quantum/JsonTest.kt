package applications.quantum

fun main(args: Array<String>) {
    println(QuantumBasis.eyeBasis(3).states[2].toJSON.toJsonString())
    println(BlochVector.from(QuantumBasis.eyeBasis(1).states[1]).toJSON.toJsonString())
}