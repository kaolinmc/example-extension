package net.yakclient.extensions.example

import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.SplashRenderer
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import net.minecraft.server.Bootstrap
import net.yakclient.client.api.BEFORE_END
import net.yakclient.client.api.annotation.*

@Mixin("net.minecraft.client.gui.screens.TitleScreen")
abstract class TitleScreenMixin(ignored: Component) : Screen(ignored) {
    private var splash: SplashRenderer? = null

    @SourceInjection(
            point = BEFORE_END,
            from = "net.yakclient.extensions.example.TitleScreenMixin",
            to = "net.minecraft.client.gui.screens.TitleScreen",
            methodFrom = "initV1()V",
            methodTo = "init()V",
            priority = 0
    )
    fun initV1() {
        splash = SplashRenderer("Yakclient is awesome!")
        Bootstrap.realStdoutPrintln("Hey man")
        this.addRenderableWidget(
                createTheWidget(width, height)
        )
    }

//    @SourceInjection(
//        point = AFTER_BEGIN,
//        from = "net.yakclient.extensions.example.TitleScreenMixin",
//        to = "net.minecraft.client.gui.screens.TitleScreen",
//        methodFrom = "initV2(I)V",
//        methodTo = "createNormalMenuOptions(II)V",
//        priority = 0
//    )
//    fun initV2(f: Int) {
//        Bootstrap.realStdoutPrintln("ERERE!!")
//
//        val a = {
//            val button = Button.builder(Component.literal("Some value here, idk dude")) {
//                Bootstrap.realStdoutPrintln("I WAS CLICKED!!")
//            }.bounds(width / 2 - 100, f, 200, 20).build()
//        }
////        this.addRenderableWidget(button)
//    }
}

fun createTheWidget(width: Int, height: Int): Button {
    return Button.builder(Component.literal("Click this button for magic ;)")) {
        Bootstrap.realStdoutPrintln("CLICKED!")
    }.bounds(width / 2 - 100, height / 4 + 24, 200, 20)
            .build()
}