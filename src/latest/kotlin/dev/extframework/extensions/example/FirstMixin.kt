package dev.extframework.extensions.example

import dev.extframework.core.api.mixin.InjectionContinuation
import dev.extframework.core.api.mixin.Mixin
import dev.extframework.core.api.mixin.SourceInjection
import net.minecraft.client.main.Main
import net.minecraft.server.Bootstrap

@Mixin(Main::class)
public object FirstMixin {
    @SourceInjection(
        point = "after-begin",
        methodTo = "main([Ljava/lang/String;)V",
        priority = 0
    )
    @JvmStatic
    public fun injectIt(args: Array<String>, continuation: InjectionContinuation) : InjectionContinuation.Result {
        println("Hey, me me me")
        Bootstrap.realStdoutPrintln("Hey, This got injected")
        return continuation.resume()
    }
}

fun callThis() {
    println("This has been called! ")
}