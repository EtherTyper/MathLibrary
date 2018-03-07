package applications.electrostatics

import core.differential.DescentLine
import core.vector.DoubleVector
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.GeneralPath
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingUtilities

private val chargeCluster = ChargeCluster(
        PointCharge(DoubleVector(0.0, 0.0), charge = 1.0, radius = 1.0),
        PointCharge(DoubleVector(3.0, 3.0), charge = -1.0, radius = 0.5)
)

fun main(args: Array<String>) {
    print(chargeCluster)

    SwingUtilities.invokeLater {
        Window()
    }
}

@Suppress("ConvertSecondaryConstructorToPrimary")
class Window : JFrame {
    val size = chargeCluster.upperBound.toGraphical(chargeCluster)
    val idealWidth = size[0].toInt()
    val idealHeight = size[1].toInt()

    constructor() {
        val canvas = Canvas()
        canvas.preferredSize = Dimension(idealWidth, idealHeight)

        contentPane.add(canvas)

        defaultCloseOperation = EXIT_ON_CLOSE
        pack()
        title = "Electrostatics Simulation"
        isVisible = true

        JFrame()
    }
}

class Canvas : JPanel() {
    override fun paintComponent(g: Graphics?) {
        @Suppress("NAME_SHADOWING")
        val g = g as Graphics2D

        super.paintComponent(g)
        background = Color.BLACK

        g.color = Color.GRAY
        for (charge in chargeCluster.charges) {
            charge.draw(g, chargeCluster)
        }

        g.color = Color.RED

        val descentPath = DescentLine(1.0, DoubleVector(2.0, 4.0), chargeCluster.potential, chargeCluster::isInBounds)

        val path = GeneralPath(GeneralPath.WIND_EVEN_ODD, descentPath.fieldPointArray.size)

        descentPath.fieldPointArray.forEachIndexed({ index: Int, fieldPoint: DoubleVector ->
            val graphicalPointLocation = fieldPoint.toGraphical(chargeCluster)

            if (index == 0)
                path.moveTo(
                        graphicalPointLocation[0],
                        graphicalPointLocation[1]
                )
            else
                path.lineTo(
                        graphicalPointLocation[0],
                        graphicalPointLocation[1]
                )
        })

        g.draw(path)
    }
}