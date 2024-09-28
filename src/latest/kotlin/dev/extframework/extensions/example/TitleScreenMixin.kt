package dev.extframework.extensions.example

import dev.extframework.core.api.mixin.InjectionContinuation
import dev.extframework.core.api.mixin.Mixin
import dev.extframework.core.api.mixin.SourceInjection
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.SplashRenderer
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.gui.screens.TitleScreen
import net.minecraft.network.chat.Component
import net.minecraft.server.Bootstrap

@Mixin(TitleScreen::class)
abstract class TitleScreenMixin(ignored: Component) : Screen(ignored) {
    private var splash: SplashRenderer? = null

    @SourceInjection(
            point = "before-end",
            methodTo = "init()V",
            priority = 0
    )
    fun initV1(continuation: InjectionContinuation) : InjectionContinuation.Result {
        splash = SplashRenderer("Yakclient is awesome!")
        Bootstrap.realStdoutPrintln("Hey man")
        this.addRenderableWidget(
                createTheWidget(width, height)
        )
        return continuation.resume()
    }
}

fun createTheWidget(width: Int, height: Int): Button {
    return Button.builder(Component.literal("Click this button for magic ;)")) {
        Bootstrap.realStdoutPrintln("CLICKED!")
    }.bounds(width / 2 - 100, height / 4 + 24, 200, 20)
            .build()
}