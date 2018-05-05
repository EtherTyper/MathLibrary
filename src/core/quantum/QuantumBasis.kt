package core.quantum

open class QuantumBasis(vararg val states: QuantumState): Iterable<QuantumState> {
    override fun iterator() = states.iterator()

//    companion object {
//        val eyeBasis =
//    }
}