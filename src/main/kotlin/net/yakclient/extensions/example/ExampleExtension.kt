package net.yakclient.extensions.example

import net.yakclient.client.api.Extension
import java.io.PrintStream

public class ExampleExtension : Extension() {
    public companion object {
        val out: PrintStream = System.out
    }

    override fun cleanup() {
        println("nothing")
    }
    override fun init() {
        out.println("Initing!! more different")
        registerBlocks()
    }
}
