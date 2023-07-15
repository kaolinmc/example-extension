package net.yakclient.extensions.example

import net.yakclient.client.api.AFTER_BEGIN
import net.yakclient.client.api.BEFORE_END
import net.yakclient.client.api.annotation.*;

@Mixin("net.minecraft.client.gui.screens.TitleScreen")
abstract class TitleScreenMixin {
    private var splash: String = ""

    @SourceInjection(
        point = BEFORE_END,
        from = "net.yakclient.extensions.example.TitleScreenMixin",
        to = "net.minecraft.client.gui.screens.TitleScreen",
        methodFrom = "changeSplash()V",
        methodTo = "init()V",
        priority = 0
    )
    fun changeSplash() {
        splash = "YakClient is awesome!"
    }
}

@Mixin("net.minecraft.client.renderer.PanoramaRenderer")
abstract class PanoramaMixin {
    private var time: Float = 0f

    @SourceInjection(
        point = AFTER_BEGIN,
        from  = "net.yakclient.extensions.example.PanoramaMixin",
        to = "net.minecraft.client.renderer.PanoramaRenderer",
        methodFrom="speedupPanorama(F)V",
        methodTo="render(FF)V",
        priority = 1
    )
    fun speedupPanorama(time: Float) {
        this.time += time * 2 // Will go three times faster.
    }
}