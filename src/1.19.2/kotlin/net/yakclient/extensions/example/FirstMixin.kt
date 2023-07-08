package net.yakclient.extensions.example

import net.yakclient.client.api.annotation.Mixin
import net.yakclient.client.api.annotation.SourceInjection

@Mixin("net.minecraft.client.main.Main")
public abstract class FirstMixin {
    @SourceInjection(
        point = "net.yakclient.components.yak.mixin.InjectionPoints\$AfterBegin",
        from = "net.yakclient.extensions.example.FirstMixin",
        to = "net.minecraft.client.main.Main",
        methodFrom = "injectIt()V",
        methodTo = "main([Ljava/lang/String;)V",
        priority = 0
    )
    public fun injectIt() {
        println("Testing??? Is this injected??")
    }

}