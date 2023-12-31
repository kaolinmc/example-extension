package net.yakclient.extensions.example

import jdk.jfr.StackTrace
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.server.Bootstrap
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.yakclient.client.api.AFTER_BEGIN
import net.yakclient.client.api.annotation.Mixin
import net.yakclient.client.api.annotation.SourceInjection
import java.util.function.Supplier

@Mixin("net.minecraft.client.main.Main")
public abstract class FirstMixin {
    @SourceInjection(
        point = AFTER_BEGIN,
        methodTo = "main([Ljava/lang/String;)V",
        priority = 0
    )
    public fun injectIt() {
        println("Hey, me me me")
        Bootstrap.realStdoutPrintln("Hey, This got injected")
    }
}

fun callThis() {
    println("This has been called! ")
}