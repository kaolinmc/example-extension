package dev.extframework.extensions.example

import dev.extframework.core.api.mixin.InjectionContinuation
import dev.extframework.core.api.mixin.Mixin
import dev.extframework.core.api.mixin.SourceInjection
import net.minecraft.core.registries.BuiltInRegistries

@Mixin(BuiltInRegistries::class)
object BootstrapCallbackMixin {
    @JvmStatic
    @SourceInjection(point = "after-begin", methodTo = "freeze()V", priority = 0)
    fun overwriteFreeze(continuation: InjectionContinuation) : InjectionContinuation.Result {
        for (bootstrapCallback in bootstrapCallbacks) {
            bootstrapCallback.invoke()
        }
        
        return continuation.resume()
    }
}