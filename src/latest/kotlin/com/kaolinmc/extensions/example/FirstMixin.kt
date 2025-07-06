package com.kaolinmc.extensions.example

import com.kaolinmc.mixin.api.InjectCode
import com.kaolinmc.mixin.api.Mixin
import net.minecraft.client.main.Main


@Mixin(Main::class)
object FirstMixin {
    @InjectCode
    @JvmStatic
    fun main() {
        println("An injection into main")
    }
}