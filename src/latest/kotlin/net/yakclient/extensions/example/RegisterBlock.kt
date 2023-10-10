package net.yakclient.extensions.example

import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.Bootstrap
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.PathPackResources
import net.minecraft.server.packs.resources.*
import net.minecraft.sounds.SoundEvents
import net.minecraft.util.profiling.ProfilerFiller
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import net.yakclient.client.api.BEFORE_END
import net.yakclient.client.api.annotation.Mixin
import net.yakclient.client.api.annotation.SourceInjection
import net.yakclient.extensions.example.tweaker.ExampleTweaker
import java.net.URI
import java.nio.file.Path
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
import kotlin.io.path.name

public class MyCustomItem(properties: Properties) : Item(properties) {
    override fun getName(`$$0`: ItemStack): Component {
        return Component.literal("Custom Item")
    }

    override fun getDescription(): Component {
        return Component.literal("By YakClient!")
    }

    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResultHolder<ItemStack> {
        player.playSound(SoundEvents.BELL_RESONATE)
        Bootstrap.realStdoutPrintln("They used my item!")
        return super.use(level, player, hand)
    }
}

//@Mixin("net.minecraft.server.packs.resources.FallbackResourceManager")
//abstract class ResourceManagerMixin {
//    @SourceInjection(
//        point = AFTER_BEGIN,
//        from = "net.yakclient.extensions.example.ResourceManagerMixin",
//        to = "net.minecraft.server.packs.resources.FallbackResourceManager",
//        methodFrom = "injectGetResource(Lnet/minecraft/resources/ResourceLocation;)V",
//        methodTo = "getResource(Lnet/minecraft/resources/ResourceLocation;)Ljava/util/Optional;",
//        priority = 0
//    )
//    fun injectGetResource(resource: ResourceLocation) {
//        Bootstrap.realStdoutPrintln(resource.toString())
//    }
//}

//@Mixin("net.minecraft.server.packs.resources.MultiPackResourceManager")
//abstract class MultiPackResourceManagerMixin {
//    private val namespacedManagers: MutableMap<String, FallbackResourceManager>? = null
//
//    @SourceInjection(
//        point = BEFORE_END,
//        from = "net.yakclient.extensions.example.MultiPackResourceManagerMixin",
//        to = "net.minecraft.server.packs.resources.MultiPackResourceManager",
//        methodFrom = "injectConstructor()V",
//        methodTo = "<init>(Lnet/minecraft/server/packs/PackType;Ljava/util/List;)V",
//        priority = 0
//    )
//    fun injectConstructor() {
//        Bootstrap.realStdoutPrintln("-------" + namespacedManagers.toString())
//    }
//}

@Mixin("net.minecraft.world.item.Items")
abstract class RegisterBlocksMixin {
    @SourceInjection(
        point = BEFORE_END,
        from = "net.yakclient.extensions.example.RegisterBlocksMixin",
        to = "net.minecraft.world.item.Items",
        methodFrom = "registerItem()V",
        methodTo = "<clinit>()V",
        priority = 0
    )
    fun registerItem() {
        Bootstrap.realStdoutPrintln("Item is about to be registered")
//        Registry.register(
//                BuiltInRegistries.ITEM,
//                ResourceLocation("yakclient", "custom_item"),
//                MyCustomItem(Item.Properties())
//        )
        Items.registerItem(
            ResourceLocation("yakclient", "custom_item"),
            MyCustomItem(Item.Properties())
        )
    }
}


@Mixin("net.minecraft.server.packs.resources.MultiPackResourceManager")
abstract class AddYakClientNamespace {
    private val namespacedManagers: MutableMap<String, FallbackResourceManager> = HashMap()

    @SourceInjection(
        point = BEFORE_END,
        from = "net.yakclient.extensions.example.AddYakClientNamespace",
        to = "net.minecraft.server.packs.resources.MultiPackResourceManager",
        methodFrom = "injectIt()V",
        methodTo = "<init>(Lnet/minecraft/server/packs/PackType;Ljava/util/List;)V"
    )
    fun injectIt() {
        Bootstrap.realStdoutPrintln("Yakclient should be added")
        val pack = FallbackResourceManager(PackType.CLIENT_RESOURCES, "yakclient")
        pack.push(
            ArchiveReferencePackResources("aa", ExampleTweaker.extensionsToRegister.first(), true)
        )

        namespacedManagers["aa"] = pack
    }


}
private fun getResource(name: String) = IoSupplier {
    ExampleExtension::class.java.getResourceAsStream(
        name
    )
}

@Mixin("net.minecraft.client.Minecraft")
abstract class RegisterInit {
    private val resourceManager: ReloadableResourceManager
        get() = throw NoSuchFieldError()

//    @SourceInjection(
//        point = AFTER_BEGIN,
//        from = "net.yakclient.extensions.example.RegisterInit",
//        to = "net.minecraft.client.Minecraft",
//        methodFrom = "onRun()V",
//        methodTo = "run()V",
//    )
//    fun onRun() {
////        val pack = FallbackResourceManager(PackType.CLIENT_RESOURCES, "yakclient")
////        pack.push(FilePackResources("yakclient", File(ExampleExtension::class.java.getResource("")!!.toURI()), false))
//
////        Bootstrap.realStdoutPrintln("HERE REGISTERING Tsi THING")
////        val r = runCatching {
////            Bootstrap.realStdoutPrintln("AgAgiNA giNa")
////
////            registerIt(resourceManager)
////        }
////
////        Bootstrap.resourceManagerrealStdoutPrintln(r.exceptionOrNull()?.toString() ?: "Uh GOD?")
//    }
}

//class MyManager(type: PackType, str: String) : FallbackResourceManager(type, str) {
//    override fun getNamespaces(): MutableSet<String> {
//        return mutableSetOf("yakclient")
//    }
//
//    over
//
//    override fun listPacks(): Stream<PackResources> {
//        return super.listPacks()
//    }
//}


fun jarFile(): Path {
    Bootstrap.realStdoutPrintln("Finding jar file")

    fun find(path: Path): Path {
        if (path.name == "example-extension-1.0-SNAPSHOT.jar!") {
            return path.parent.resolve("example-extension-1.0-SNAPSHOT.jar")
        } else {
            return find(path.parent)
        }
    }

    val p = Path.of(URI.create(ExampleExtension::class.java.getResource("")!!.file))

    Bootstrap.realStdoutPrintln(p.toString())

    return p
}

fun registerIt(resourceManager: ReloadableResourceManager) {
    Bootstrap.realStdoutPrintln("Hey hey hey!!!")
    resourceManager.registerReloadListener(object : PreparableReloadListener {
        override fun reload(
            barrier: PreparableReloadListener.PreparationBarrier,
            manager: ResourceManager,
            filler1: ProfilerFiller,
            filler2: ProfilerFiller,
            executor1: Executor,
            executor2: Executor
        ): CompletableFuture<Void> {
            return CompletableFuture.runAsync {
                manager.listResources("") {
                    it.path.endsWith(".json")
                }
            }
        }

    })
}

fun testPathPack() {
    val pack = ArchiveReferencePackResources("yakclient", ExampleTweaker.extensionsToRegister.first(), true)
    pack.getNamespaces(PackType.CLIENT_RESOURCES)

    println("here")
}