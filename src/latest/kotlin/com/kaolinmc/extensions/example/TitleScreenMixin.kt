package com.kaolinmc.extensions.example

import com.kaolinmc.mixin.api.InjectCode
import com.kaolinmc.mixin.api.InjectionBoundary
import com.kaolinmc.mixin.api.Mixin
import com.kaolinmc.mixin.api.Select
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.SplashRenderer
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.TitleScreen
import net.minecraft.network.chat.Component
import net.minecraft.server.Bootstrap

//@Mixin(TitleScreen::class)
//abstract class TitleScreenMixin(ignored: Component) : Screen(ignored) {
//    private var splash: SplashRenderer? = null
//
//    @InjectCode(
//        "<init>",
//        point = Select(
//            InjectionBoundary.TAIL
//        )
//    )
//    fun initV1() {
//        splash = SplashRenderer("Kaolin is awesome!")
//        this.addRenderableWidget(
//            createTheWidget(width, height)
//        )
//    }
//}

fun createTheWidget(width: Int, height: Int): Button {
    return Button.builder(Component.literal("Click this button for magic ;)")) {
        Bootstrap.realStdoutPrintln("CLICKED!")
    }.bounds(width / 2 - 100, height / 4 + 24, 200, 20)
        .build()
}