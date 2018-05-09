package applications.quantum

import core.vector.DoubleVector
import javax.swing.JFrame
import javax.swing.SwingUtilities

private var quantumState = QuantumState(1, *DoubleVector(1.0, 0.0).dimensions)

fun main(args: Array<String>) {
    SwingUtilities.invokeLater {
        Window()
    }
}

@Suppress("ConvertSecondaryConstructorToPrimary")
class Window : JFrame() {
    init {

    }
}