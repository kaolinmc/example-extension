package net.yakclient.extensions.example

import net.yakclient.client.api.Extension
import net.yakclient.client.api.ExtensionContext

public class ExampleExtension : Extension() {
    override fun init(context: ExtensionContext) {
        println("Initing!!")
        getSomething()
    }

    override fun cleanup() {
        println("nothing")
    }
}
