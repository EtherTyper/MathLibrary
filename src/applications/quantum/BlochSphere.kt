package applications.quantum

import core.vector.DoubleVector
import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Parent
import javafx.scene.PerspectiveCamera
import javafx.scene.Scene
import javafx.scene.SubScene
import javafx.scene.paint.Color
import javafx.scene.paint.PhongMaterial
import javafx.scene.shape.Box
import javafx.scene.shape.DrawMode
import javafx.scene.transform.Rotate
import javafx.scene.transform.Translate
import javafx.stage.Stage

private var quantumState = QuantumState(1, *DoubleVector(1.0, 0.0).dimensions)

open class BlochSphere : Application() {
    @Throws(Exception::class)
    fun createContent(): Parent {
        // Box
        val testBox = Box(5.0, 5.0, 5.0)
        testBox.material = PhongMaterial(Color.RED)
        testBox.drawMode = DrawMode.LINE

        // Create and position camera
        val camera = PerspectiveCamera(true)
        camera.transforms.addAll(
                Rotate(-20.0, Rotate.Y_AXIS),
                Rotate(-20.0, Rotate.X_AXIS),
                Translate(0.0, 0.0, -15.0))

        // Build the Scene Graph
        val root = Group()
        root.children.add(camera)
        root.children.add(testBox)

        // Use a SubScene
        val subScene = SubScene(root, 300.0, 300.0)
        subScene.fill = Color.ALICEBLUE
        subScene.camera = camera
        val group = Group()
        group.children.add(subScene)
        return group
    }

    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {
        primaryStage.isResizable = false
        val scene = Scene(createContent())
        primaryStage.scene = scene
        primaryStage.show()
    }

    companion object {
        /**
         * Java main for when running without JavaFX launcher
         */
        @JvmStatic
        fun main(args: Array<String>) {
            launch(BlochSphere::class.java)
        }
    }
}