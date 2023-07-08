package net.yakclient.extensions.example

import net.yakclient.client.api.Extension
import net.yakclient.client.api.ExtensionContext

public class ExampleExtension : Extension() {
    override fun cleanup() {
        println("nothing")
    }
    override fun init() {
        println("Initing!!")
        getSomething()
    }
}
