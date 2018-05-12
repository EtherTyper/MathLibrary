//import applications.quantum.BlochVector
//import applications.quantum.QuantumState
//import applications.quantum.toQuantumState
//import io.javalin.Javalin
//
//fun main(args: Array<String>) {
//    val app = Javalin.start(7000)
//
//    app.get("/bloch_vector:state") { ctx ->
//        ctx.result("${BlochVector.from(ctx.param("state")!!.toQuantumState())}")
//    }
//}