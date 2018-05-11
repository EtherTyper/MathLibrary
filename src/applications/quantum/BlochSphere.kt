/*
 * Copyright (c) 2013, 2014 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package moleculesampleapp

import applications.quantum.QuantumState
import core.vector.DoubleVector
import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.*
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.scene.paint.PhongMaterial
import javafx.scene.shape.Box
import javafx.scene.shape.Cylinder
import javafx.scene.shape.Sphere
import javafx.scene.transform.Rotate
import javafx.scene.transform.Scale
import javafx.scene.transform.Translate
import javafx.stage.Stage

private var quantumState = QuantumState(1, *DoubleVector(1.0, 0.0).dimensions)

class Xform : Group {
    var t = Translate()
    var p = Translate()
    var ip = Translate()
    var rx = Rotate()
    var ry = Rotate()
    var rz = Rotate()
    var s = Scale()

    enum class RotateOrder {
        XYZ, XZY, YXZ, YZX, ZXY, ZYX
    }

    init {
        rx.axis = Rotate.X_AXIS
    }

    init {
        ry.axis = Rotate.Y_AXIS
    }

    init {
        rz.axis = Rotate.Z_AXIS
    }

    constructor() : super() {
        transforms.addAll(t, rz, ry, rx, s)
    }

    constructor(rotateOrder: RotateOrder) : super() {
        // choose the order of rotations based on the rotateOrder
        when (rotateOrder) {
            Xform.RotateOrder.XYZ -> transforms.addAll(t, p, rz, ry, rx, s, ip)
            Xform.RotateOrder.XZY -> transforms.addAll(t, p, ry, rz, rx, s, ip)
            Xform.RotateOrder.YXZ -> transforms.addAll(t, p, rz, rx, ry, s, ip)
            Xform.RotateOrder.YZX -> transforms.addAll(t, p, rx, rz, ry, s, ip)  // For Camera
            Xform.RotateOrder.ZXY -> transforms.addAll(t, p, ry, rx, rz, s, ip)
            Xform.RotateOrder.ZYX -> transforms.addAll(t, p, rx, ry, rz, s, ip)
        }
    }

    fun setTranslate(x: Double, y: Double, z: Double) {
        t.x = x
        t.y = y
        t.z = z
    }

    fun setTranslate(x: Double, y: Double) {
        t.x = x
        t.y = y
    }

    // Cannot override these methods as they are final:
    // public void setTranslateX(double x) { t.setX(x); }
    // public void setTranslateY(double y) { t.setY(y); }
    // public void setTranslateZ(double z) { t.setZ(z); }
    // Use these methods instead:
    fun setTx(x: Double) {
        t.x = x
    }

    fun setTy(y: Double) {
        t.y = y
    }

    fun setTz(z: Double) {
        t.z = z
    }

    fun setRotate(x: Double, y: Double, z: Double) {
        rx.angle = x
        ry.angle = y
        rz.angle = z
    }

    fun setRotateX(x: Double) {
        rx.angle = x
    }

    fun setRotateY(y: Double) {
        ry.angle = y
    }

    fun setRotateZ(z: Double) {
        rz.angle = z
    }

    fun setRx(x: Double) {
        rx.angle = x
    }

    fun setRy(y: Double) {
        ry.angle = y
    }

    fun setRz(z: Double) {
        rz.angle = z
    }

    fun setScale(scaleFactor: Double) {
        s.x = scaleFactor
        s.y = scaleFactor
        s.z = scaleFactor
    }

    fun setScale(x: Double, y: Double, z: Double) {
        s.x = x
        s.y = y
        s.z = z
    }

    // Cannot override these methods as they are final:
    // public void setScaleX(double x) { s.setX(x); }
    // public void setScaleY(double y) { s.setY(y); }
    // public void setScaleZ(double z) { s.setZ(z); }
    // Use these methods instead:
    fun setSx(x: Double) {
        s.x = x
    }

    fun setSy(y: Double) {
        s.y = y
    }

    fun setSz(z: Double) {
        s.z = z
    }

    fun setPivot(x: Double, y: Double, z: Double) {
        p.x = x
        p.y = y
        p.z = z
        ip.x = -x
        ip.y = -y
        ip.z = -z
    }

    fun reset() {
        t.x = 0.0
        t.y = 0.0
        t.z = 0.0
        rx.angle = 0.0
        ry.angle = 0.0
        rz.angle = 0.0
        s.x = 1.0
        s.y = 1.0
        s.z = 1.0
        p.x = 0.0
        p.y = 0.0
        p.z = 0.0
        ip.x = 0.0
        ip.y = 0.0
        ip.z = 0.0
    }

    fun resetTSP() {
        t.x = 0.0
        t.y = 0.0
        t.z = 0.0
        s.x = 1.0
        s.y = 1.0
        s.z = 1.0
        p.x = 0.0
        p.y = 0.0
        p.z = 0.0
        ip.x = 0.0
        ip.y = 0.0
        ip.z = 0.0
    }

    override fun toString(): String {
        return "Xform[t = (" +
                t.x + ", " +
                t.y + ", " +
                t.z + ")  " +
                "r = (" +
                rx.angle + ", " +
                ry.angle + ", " +
                rz.angle + ")  " +
                "s = (" +
                s.x + ", " +
                s.y + ", " +
                s.z + ")  " +
                "p = (" +
                p.x + ", " +
                p.y + ", " +
                p.z + ")  " +
                "ip = (" +
                ip.x + ", " +
                ip.y + ", " +
                ip.z + ")]"
    }
}

/**
 *
 * @author cmcastil
 */
class MoleculeSampleApp : Application() {

    internal val root = Group()
    internal val axisGroup = Xform()
    internal val moleculeGroup = Xform()
    internal val world = Xform()
    internal val camera = PerspectiveCamera(true)
    internal val cameraXform = Xform()
    internal val cameraXform2 = Xform()
    internal val cameraXform3 = Xform()

    internal var mousePosX: Double = 0.toDouble()
    internal var mousePosY: Double = 0.toDouble()
    internal var mouseOldX: Double = 0.toDouble()
    internal var mouseOldY: Double = 0.toDouble()
    internal var mouseDeltaX: Double = 0.toDouble()
    internal var mouseDeltaY: Double = 0.toDouble()

    private fun buildCamera() {
        println("buildCamera()")
        root.children.add(cameraXform)
        cameraXform.getChildren().add(cameraXform2)
        cameraXform2.getChildren().add(cameraXform3)
        cameraXform3.getChildren().add(camera)
        cameraXform3.setRotateZ(180.0)

        camera.nearClip = CAMERA_NEAR_CLIP
        camera.farClip = CAMERA_FAR_CLIP
        camera.translateZ = CAMERA_INITIAL_DISTANCE
        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE)
        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE)
    }

    private fun buildAxes() {
        println("buildAxes()")
        val redMaterial = PhongMaterial()
        redMaterial.diffuseColor = Color.DARKRED
        redMaterial.specularColor = Color.RED

        val greenMaterial = PhongMaterial()
        greenMaterial.diffuseColor = Color.DARKGREEN
        greenMaterial.specularColor = Color.GREEN

        val blueMaterial = PhongMaterial()
        blueMaterial.diffuseColor = Color.DARKBLUE
        blueMaterial.specularColor = Color.BLUE

        val xAxis = Box(AXIS_LENGTH, 1.0, 1.0)
        val yAxis = Box(1.0, AXIS_LENGTH, 1.0)
        val zAxis = Box(1.0, 1.0, AXIS_LENGTH)

        xAxis.material = redMaterial
        yAxis.material = greenMaterial
        zAxis.material = blueMaterial

        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis)
        axisGroup.setVisible(false)
        world.getChildren().addAll(axisGroup)
    }

    private fun handleMouse(scene: Scene, root: Node) {
        scene.onMousePressed = EventHandler { me ->
            mousePosX = me.sceneX
            mousePosY = me.sceneY
            mouseOldX = me.sceneX
            mouseOldY = me.sceneY
        }
        scene.onMouseDragged = EventHandler { me ->
            mouseOldX = mousePosX
            mouseOldY = mousePosY
            mousePosX = me.sceneX
            mousePosY = me.sceneY
            mouseDeltaX = mousePosX - mouseOldX
            mouseDeltaY = mousePosY - mouseOldY

            var modifier = 1.0

            if (me.isControlDown) {
                modifier = CONTROL_MULTIPLIER
            }
            if (me.isShiftDown) {
                modifier = SHIFT_MULTIPLIER
            }
            if (me.isPrimaryButtonDown) {
                cameraXform.ry.setAngle(cameraXform.ry.getAngle() - mouseDeltaX * MOUSE_SPEED * modifier * ROTATION_SPEED)
                cameraXform.rx.setAngle(cameraXform.rx.getAngle() + mouseDeltaY * MOUSE_SPEED * modifier * ROTATION_SPEED)
            } else if (me.isSecondaryButtonDown) {
                val z = camera.translateZ
                val newZ = z + mouseDeltaX * MOUSE_SPEED * modifier
                camera.translateZ = newZ
            } else if (me.isMiddleButtonDown) {
                cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX * MOUSE_SPEED * modifier * TRACK_SPEED)
                cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY * MOUSE_SPEED * modifier * TRACK_SPEED)
            }
        }
    }

    private fun handleKeyboard(scene: Scene, root: Node) {
        scene.onKeyPressed = EventHandler { event ->
            when (event.code) {
                KeyCode.Z -> {
                    cameraXform2.t.setX(0.0)
                    cameraXform2.t.setY(0.0)
                    camera.translateZ = CAMERA_INITIAL_DISTANCE
                    cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE)
                    cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE)
                }
                KeyCode.X -> axisGroup.setVisible(!axisGroup.isVisible())
                KeyCode.V -> moleculeGroup.setVisible(!moleculeGroup.isVisible())
            }
        }
    }

    private fun buildMolecule() {
        //======================================================================
        // THIS IS THE IMPORTANT MATERIAL FOR THE TUTORIAL
        //======================================================================

        val redMaterial = PhongMaterial()
        redMaterial.diffuseColor = Color.DARKRED
        redMaterial.specularColor = Color.RED

        val whiteMaterial = PhongMaterial()
        whiteMaterial.diffuseColor = Color.WHITE
        whiteMaterial.specularColor = Color.LIGHTBLUE

        val greyMaterial = PhongMaterial()
        greyMaterial.diffuseColor = Color.DARKGREY
        greyMaterial.specularColor = Color.GREY

        // Molecule Hierarchy
        // [*] moleculeXform
        //     [*] oxygenXform
        //         [*] oxygenSphere
        //     [*] hydrogen1SideXform
        //         [*] hydrogen1Xform
        //             [*] hydrogen1Sphere
        //         [*] bond1Cylinder
        //     [*] hydrogen2SideXform
        //         [*] hydrogen2Xform
        //             [*] hydrogen2Sphere
        //         [*] bond2Cylinder
        val moleculeXform = Xform()
        val oxygenXform = Xform()
        val hydrogen1SideXform = Xform()
        val hydrogen1Xform = Xform()
        val hydrogen2SideXform = Xform()
        val hydrogen2Xform = Xform()

        val oxygenSphere = Sphere(40.0)
        oxygenSphere.material = redMaterial

        val hydrogen1Sphere = Sphere(30.0)
        hydrogen1Sphere.material = whiteMaterial
        hydrogen1Sphere.translateX = 0.0

        val hydrogen2Sphere = Sphere(30.0)
        hydrogen2Sphere.material = whiteMaterial
        hydrogen2Sphere.translateZ = 0.0

        val bond1Cylinder = Cylinder(5.0, 100.0)
        bond1Cylinder.material = greyMaterial
        bond1Cylinder.translateX = 50.0
        bond1Cylinder.rotationAxis = Rotate.Z_AXIS
        bond1Cylinder.rotate = 90.0

        val bond2Cylinder = Cylinder(5.0, 100.0)
        bond2Cylinder.material = greyMaterial
        bond2Cylinder.translateX = 50.0
        bond2Cylinder.rotationAxis = Rotate.Z_AXIS
        bond2Cylinder.rotate = 90.0

        moleculeXform.getChildren().add(oxygenXform)
        moleculeXform.getChildren().add(hydrogen1SideXform)
        moleculeXform.getChildren().add(hydrogen2SideXform)
        oxygenXform.getChildren().add(oxygenSphere)
        hydrogen1SideXform.getChildren().add(hydrogen1Xform)
        hydrogen2SideXform.getChildren().add(hydrogen2Xform)
        hydrogen1Xform.getChildren().add(hydrogen1Sphere)
        hydrogen2Xform.getChildren().add(hydrogen2Sphere)
        hydrogen1SideXform.getChildren().add(bond1Cylinder)
        hydrogen2SideXform.getChildren().add(bond2Cylinder)

        hydrogen1Xform.setTx(100.0)
        hydrogen2Xform.setTx(100.0)
        hydrogen2SideXform.setRotateY(HYDROGEN_ANGLE)

        moleculeGroup.getChildren().add(moleculeXform)

        world.getChildren().addAll(moleculeGroup)
    }

    override fun start(primaryStage: Stage) {

        // setUserAgentStylesheet(STYLESHEET_MODENA);
        println("start()")

        root.children.add(world)
        root.depthTest = DepthTest.ENABLE

        // buildScene();
        buildCamera()
        buildAxes()
        buildMolecule()

        val scene = Scene(root, 1024.0, 768.0, true)
        scene.fill = Color.GREY
        handleKeyboard(scene, world)
        handleMouse(scene, world)

        primaryStage.title = "Molecule Sample Application"
        primaryStage.scene = scene
        primaryStage.show()

        scene.camera = camera
    }

    companion object {
        private val CAMERA_INITIAL_DISTANCE = -450.0
        private val CAMERA_INITIAL_X_ANGLE = 70.0
        private val CAMERA_INITIAL_Y_ANGLE = 320.0
        private val CAMERA_NEAR_CLIP = 0.1
        private val CAMERA_FAR_CLIP = 10000.0
        private val AXIS_LENGTH = 250.0
        private val HYDROGEN_ANGLE = 104.5
        private val CONTROL_MULTIPLIER = 0.1
        private val SHIFT_MULTIPLIER = 10.0
        private val MOUSE_SPEED = 0.1
        private val ROTATION_SPEED = 2.0
        private val TRACK_SPEED = 0.3

        /**
         * The main() method is ignored in correctly deployed JavaFX application.
         * main() serves only as fallback in case the application can not be
         * launched through deployment artifacts, e.g., in IDEs with limited FX
         * support. NetBeans ignores main().
         *
         * @param args the command line arguments
         */
        @JvmStatic
        fun main(args: Array<String>) {
            launch(MoleculeSampleApp::class.java)
        }
    }

}
