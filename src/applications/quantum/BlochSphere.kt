package applications.quantum

import io.javalin.Context
import io.javalin.Javalin
import java.util.*

val String.decoded get() = String(Base64.getDecoder().decode(this))
val Context.state get() = this.queryParam("state")!!.decoded.toQuantumState()
fun Context.result(state: QuantumState) = this.result(state.componentString)

fun main(args: Array<String>) {
    val app = Javalin.start(7000)

    val gates = mapOf(
            "I" to QuantumGate.identity(1),
            "H" to QuantumGate.H,
            "sqrtNOT" to QuantumGate.sqrtNOT,
            "X" to QuantumGate.X,
            "Y" to QuantumGate.Y,
            "Z" to QuantumGate.Z
    )

    app.get("/bloch_vector") { ctx ->
        ctx.result("${BlochVector.from(ctx.state)}")
    }

    app.get("/apply") { ctx ->
        ctx.result(gates[ctx.queryParam("gate")]!! * ctx.state)
    }

    app.get("/measure") { ctx ->
        ctx.result(ctx.state.measure(QuantumBasis.eyeBasis(1)))
    }

    app.get("/rotate") { ctx ->
        ctx.result(QuantumGate.R(ctx.queryParam("theta")!!.toDouble()) * ctx.state)
    }
}