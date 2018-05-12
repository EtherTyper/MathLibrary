package applications.electrostatics

import core.differential.DescentLine
import core.differential.Isocline
import core.vector.DoubleVector
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.geom.GeneralPath
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingUtilities

private var chargeCluster = ChargeCluster()

fun main(args: Array<String>) {
    SwingUtilities.invokeLater {
        Window()
    }
}

class Window() : JFrame() {
    val size get() = chargeCluster.upperBound.toGraphical(chargeCluster)
    val idealWidth get() = size[0].toInt()
    val idealHeight get() = size[1].toInt()

    init {
        val canvas = Canvas()
        canvas.preferredSize = Dimension(idealWidth, idealHeight)

        canvas.addMouseListener(object : MouseAdapter() {
            override fun mouseReleased(e: MouseEvent?) {
                super.mouseReleased(e)

                if (e != null)
                    chargeCluster = ChargeCluster(*chargeCluster.charges,
                            PointCharge(
                                    DoubleVector(e.point.x.toDouble(), e.point.y.toDouble()).fromGraphical(chargeCluster),
                                    when {
                                        SwingUtilities.isLeftMouseButton(e) -> 1.0
                                        SwingUtilities.isRightMouseButton(e) -> -1.0
                                        else -> 0.0
                                    },
                                    0.5
                            )
                    )

                canvas.preferredSize = Dimension(idealWidth, idealHeight)
                repaint()
                pack()
            }
        })

        contentPane.add(canvas)

        defaultCloseOperation = EXIT_ON_CLOSE
        pack()
        title = "Electrostatics Simulation"
        isVisible = true
    }
}

fun drawPointArray(g: Graphics2D, points: Collection<DoubleVector>) {
    val graphicalPath = GeneralPath(GeneralPath.WIND_EVEN_ODD, points.size)

    points.forEachIndexed({ index: Int, fieldPoint: DoubleVector ->
        val graphicalPointLocation = fieldPoint.toGraphical(chargeCluster)

        if (index == 0)
            graphicalPath.moveTo(
                    graphicalPointLocation[0],
                    graphicalPointLocation[1]
            )
        else
            graphicalPath.lineTo(
                    graphicalPointLocation[0],
                    graphicalPointLocation[1]
            )
    })

    g.draw(graphicalPath)
}

class Canvas : JPanel() {
    override fun paintComponent(g: Graphics?) {
        @Suppress("NAME_SHADOWING")
        val g = g as Graphics2D

        super.paintComponent(g)
        background = Color.BLACK

        for (charge in chargeCluster) {
            g.color = when {
                charge.charge > 0 -> Color.BLUE
                charge.charge < 0 -> Color.RED
                else -> Color.GRAY
            }

            charge.draw(g, chargeCluster)
        }

        g.color = Color.RED

        val isoclineStartPoints = mutableListOf<DoubleVector>()

        for (charge in chargeCluster) {
            if (charge.charge > 0) {
                for (surfacePoint in charge.surfacePoints(20)) {
                    // We set the isocline threshold to the max here to disable isoclines.
                    val descentPath = DescentLine(Double.MAX_VALUE, surfacePoint, chargeCluster.potential, chargeCluster::isInBounds)
                    drawPointArray(g, descentPath.fieldPointArray)

                    isoclineStartPoints.addAll(descentPath.isoclinePointArray)
                }
            }
        }

        g.color = Color.GREEN

        for (isoclinePoint in isoclineStartPoints) {
            val isocline = Isocline(isoclinePoint, chargeCluster.potential, chargeCluster::isInBounds)

            drawPointArray(g, isocline.pointArray)
        }
    }
}