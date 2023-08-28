package net.yakclient.extensions.example

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.Bootstrap
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.resources.FallbackResourceManager
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.yakclient.client.api.AFTER_BEGIN
import net.yakclient.client.api.BEFORE_END
import net.yakclient.client.api.annotation.Mixin
import net.yakclient.client.api.annotation.SourceInjection

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

@Mixin("net.minecraft.server.packs.resources.FallbackResourceManager")
abstract class ResourceManagerMixin {
    @SourceInjection(
            point = AFTER_BEGIN,
            from = "net.yakclient.extensions.example.ResourceManagerMixin",
            to = "net.minecraft.server.packs.resources.FallbackResourceManager",
            methodFrom = "injectGetResource(Lnet/minecraft/resources/ResourceLocation;)V",
            methodTo = "getResource(Lnet/minecraft/resources/ResourceLocation;)Ljava/util/Optional;",
            priority = 0
    )
    fun injectGetResource(resource: ResourceLocation) {
        Bootstrap.realStdoutPrintln(resource.toString())
    }
}

@Mixin("net.minecraft.server.packs.resources.MultiPackResourceManager")
abstract class MultiPackResourceManagerMixin {
    private val namespacedManagers: MutableMap<String, FallbackResourceManager>? = null
    @SourceInjection(
            point = BEFORE_END,
            from = "net.yakclient.extensions.example.MultiPackResourceManagerMixin",
            to = "net.minecraft.server.packs.resources.MultiPackResourceManager",
            methodFrom = "injectConstructor()V",
            methodTo = "<init>(Lnet/minecraft/server/packs/PackType;Ljava/util/List;)V",
            priority = 0
    )
    fun injectConstructor() {
        Bootstrap.realStdoutPrintln("-------" + namespacedManagers.toString())
    }
}

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
        Registry.register(
                BuiltInRegistries.ITEM,
                ResourceLocation("yakclient", "custom_item"),
                MyCustomItem(Item.Properties())
        )
//        Items.registerItem(
//                ResourceLocation("yakclient", "custom_item"),
//                MyCustomItem(Item.Properties())
//        )
    }
}

