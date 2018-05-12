package applications.quantum

import io.javalin.Javalin
import java.util.*

val String.decoded get() = String(Base64.getDecoder().decode(this))

fun main(args: Array<String>) {
    val app = Javalin.start(7000)

    app.get("/bloch_vector") { ctx ->
        ctx.result("${BlochVector.from(ctx.queryParam("state")!!.decoded.toQuantumState())}")
    }
}