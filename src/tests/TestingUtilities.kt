package tests

abstract class Tests {
    companion object {
        fun section(name: String, block: (() -> Unit)) {
            print("\u001B[1;31m\n$name\n\n\u001B[0m")
            block()
        }

        fun shouldError(block: (() -> Unit)) {
            try {
                block()

                println("No error occurred.")
            } catch (e: Error) {
                println("Error: ${e.message}")
                println("\u001B[1;32m\u2705 Success!\u001B[0m")
            }
        }
    }
}