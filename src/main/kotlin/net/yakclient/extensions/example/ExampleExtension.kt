package net.yakclient.extensions.example

import net.yakclient.client.api.Extension
import net.yakclient.extensions.example.tweaker.ExampleTweaker
import java.io.PrintStream

public class ExampleExtension : Extension() {
    override fun cleanup() {
        println("nothing")
    }

    override fun init() {
        testPathPack()
        println("Ok inniting here")
    }
}
