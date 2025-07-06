package com.kaolinmc.extensions.example

import com.kaolinmc.core.capability.Capability0
import com.kaolinmc.core.capability.defining
import com.kaolinmc.core.entrypoint.Entrypoint
import com.kaolinmc.core.minecraft.api.TargetCapabilities
import com.kaolinmc.mdk.init.onInit

class ExampleExtension : Entrypoint() {
    companion object {
        val registerBlock by TargetCapabilities.defining<Capability0<Unit>>()
    }

    override fun init() {
        println("Ok initializing here")

        onInit {
            println("Bootstrapping")

            registerBlock.call()
        }
    }
}
