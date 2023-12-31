package net.yakclient.extensions.example;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.yakclient.client.api.InjectionPoints;
import net.yakclient.client.api.annotation.Mixin;
import net.yakclient.client.api.annotation.SourceInjection;

import static net.yakclient.extensions.example.UtilsKt.getBootstrapCallbacks;

@Mixin("net.minecraft.core.registries.BuiltInRegistries")
public abstract class BootstrapCallbackMixin {
    //Registry<T> freeze()
    @SourceInjection(
            point = InjectionPoints.AFTER_BEGIN,
            methodTo = "freeze()V",
            priority = 0
    )
    void overwriteFreeze() {
        for (Function0<Unit> bootstrapCallback : getBootstrapCallbacks()) {
            bootstrapCallback.invoke();
        }
//        createContents();
//        freeze();
//        validate(BuiltInRegistries.REGISTRY);
//        createContents();

//        createContents()
//        BuiltInRegistries.LOADERS.forEach { (`$$0`: ResourceLocation?, `$$1`: Supplier<*>) ->
//            if (`$$1`.get() == null) {
//                BuiltInRegistries.LOGGER.error("Unable to bootstrap registry '{}'", `$$0`)
//            }
//        }
//        BuiltInRegistries.freeze()
//        BuiltInRegistries.validate(BuiltInRegistries.REGISTRY)
//        Bootstrap.realStdoutPrintln("Registries not frozen")
//        createContents()
    }

    private static void createContents() {

    }
    private static void freeze() { }

    private static <T extends Registry<?>> void validate(Registry<T> $$0) {

    }
}
