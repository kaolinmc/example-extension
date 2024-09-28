package dev.extframework.extensions.example

import dev.extframework.core.api.mixin.Mixin
import net.minecraft.core.Holder
import net.minecraft.core.MappedRegistry
import net.minecraft.core.Registry

@Mixin(MappedRegistry::class)
abstract class PreventMappedRegistryFreeze<T> : Registry<T> {
    private var frozen: Boolean = false
    private val byValue: Map<T, Holder.Reference<T>> = HashMap()
//    @SourceInjection(
//        point = AFTER_BEGIN,
//        methodTo = "freeze()Lnet/minecraft/core/Registry;",
//        priority = 0
//    )
//    fun prevent() : Registry<T> {
////        Bootstrap.realStdoutPrintln("Here HERE here prevent")
////        throw IllegalArgumentException("Test")
////        if (this.frozen)
////            return this
////        this.frozen = true
//
//       doBind(byValue)
////        this.byValue.forEach(($$0, $$1) ->
////        $$1.bindValue($$0));
////        val `$$0`: List<ResourceLocation> =
////            this.byKey.entrySet().stream().filter { `$$0` -> !(`$$0`.getValue() as Holder.Reference).isBound() }
////                .map { `$$0` -> (`$$0`.getKey() as ResourceKey<*>).location() }.sorted().toList()
////        if (!$$0.isEmpty())
////        throw new  fun IllegalStateException() +": "+$$0);
////        if (this.unregisteredIntrusiveHolders != null) {
////            if (!this.unregisteredIntrusiveHolders.isEmpty())
////                throw new IllegalStateException "Some intrusive holders were not registered: " + this.unregisteredIntrusiveHolders.values();
////            this.unregisteredIntrusiveHolders = null;
////        }
//        return this
//    }
}

fun <T> doBind(byValue: Map<T, Holder.Reference<T>>) {
    byValue.forEach { t: T, u ->
        u::class.java.getDeclaredMethod("bindValue", Any::class.java)
            .also { it.trySetAccessible() }
            .invoke(u, t)
    }
}