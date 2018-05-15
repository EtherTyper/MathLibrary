package applications.quantum

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import core.complex.ComplexVector
import core.vector.DoubleVector
import io.javalin.Context
import io.javalin.Javalin
import java.util.*

val String.decoded get() = String(Base64.getDecoder().decode(this))

@Suppress("UNCHECKED_CAST")
val Context.state: QuantumState
    get() =
        QuantumState.fromJSON(
                JsonArray(
                        Klaxon().parseJsonArray(
                                Klaxon().toReader(this.queryParam("state")!!.decoded.byteInputStream())
                        ).toMutableList() as MutableList<JsonObject>
                )
        )

fun Context.result(state: ComplexVector) = this.result(state.toJSON.toJsonString())

fun main(args: Array<String>) {
    val app = Javalin.create().apply {
        enableCorsForAllOrigins()
        port(7000)
    }.start()

    val gates = mapOf(
            "I" to QuantumGate.identity(1),
            "H" to QuantumGate.H,
            "sqrtNOT" to QuantumGate.sqrtNOT,
            "X" to QuantumGate.X,
            "Y" to QuantumGate.Y,
            "Z" to QuantumGate.Z
    )

    app.get("/bloch_vector") { ctx ->
        ctx.result(BlochVector.from(ctx.state))
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