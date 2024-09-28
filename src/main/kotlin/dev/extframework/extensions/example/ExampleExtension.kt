package dev.extframework.extensions.example

import dev.extframework.core.api.Extension

class ExampleExtension : Extension() {
    override fun init() {
        asdf(5)
        println("Ok inniting here")
        onBootStrap {
            println("Boot strapping")
            registerBlock()
        }
    }
}
