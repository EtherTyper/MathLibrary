package applications.quantum

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Klaxon
import core.complex.ComplexVector
import core.vector.DoubleVector
import io.javalin.Context
import io.javalin.Javalin

@Suppress("UNCHECKED_CAST")
val Context.state
    get() = QuantumState.fromJSON(
            JsonArray(
                    Klaxon().parseJsonArray(
                            Klaxon().toReader(this.body().byteInputStream())
                    ).toMutableList() as MutableList<JsonObject>
            )
    )

fun Context.result(state: ComplexVector) = this.result(state.toJSON.toJsonString())
fun Context.result(vector: DoubleVector) = this.result(vector.toJSON.toJsonString())

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

    app.post("/bloch_vector") { ctx ->
        ctx.result(BlochVector.from(ctx.state))
    }

    app.post("/apply") { ctx ->
        ctx.result(gates[ctx.queryParam("gate")]!! * ctx.state)
    }

    app.post("/measure") { ctx ->
        ctx.result(ctx.state.measure(QuantumBasis.eyeBasis(1)))
    }

    app.post("/rotate") { ctx ->
        ctx.result(QuantumGate.R(ctx.queryParam("theta")!!.toDouble()) * ctx.state)
    }
}