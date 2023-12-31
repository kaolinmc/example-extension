package net.yakclient.extensions.example

import com.mojang.serialization.Lifecycle
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.entity.layers.RenderLayer
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.core.BlockPos
import net.minecraft.core.DefaultedMappedRegistry
import net.minecraft.core.MappedRegistry
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.Bootstrap
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.LayeredCauldronBlock
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import net.yakclient.client.api.BEFORE_END
import net.yakclient.client.api.annotation.Mixin
import net.yakclient.client.api.annotation.SourceInjection

class MyCustomItem(properties: Properties) : Item(properties) {
    override fun getName(stack: ItemStack): Component {
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

class MyCustomBlock(properties: BlockBehaviour.Properties) : Block(properties) {
    override fun use(
        `$$0`: BlockState,
        `$$1`: Level,
        `$$2`: BlockPos,
        player: Player,
        `$$4`: InteractionHand,
        `$$5`: BlockHitResult
    ): InteractionResult {
        player.sendSystemMessage(Component.literal("Hey man, howre you?"))

        return super.use(`$$0`, `$$1`, `$$2`, player, `$$4`, `$$5`)
    }

    override fun getName(): MutableComponent {
        return Component.literal("Custom Block")
    }
}

val exampleBlock = MyCustomBlock(BlockBehaviour.Properties.of().noOcclusion())

//@Mixin("net.minecraft.world.level.block.Blocks")
//abstract class RegisterBlocksMixin {
//    @SourceInjection(
//        point = BEFORE_END,
//        from = "net.yakclient.extensions.example.RegisterBlocksMixin",
//        to = "net.minecraft.world.level.block.Blocks",
//        methodFrom = "registerBlock()V",
//        methodTo = "<clinit>()V",
//        priority = 0
//    )
//    fun registerBlock() {
//        Bootstrap.realStdoutPrintln("Registering your block :)")
//        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation(
//            "yakclient", "example_block"
//        ), exampleBlock)
//    }
//}

fun attemptRegister() {
    println("here")

    Bootstrap.realStdoutPrintln("Registering your block :)")
    Registry.register(BuiltInRegistries.BLOCK, ResourceLocation(
        "yakclient", "example_block"
    ), exampleBlock)
    Items.registerItem(
        ResourceLocation("yakclient", "custom_item"),
        MyCustomItem(Item.Properties())
    )
    val blockItem = BlockItem(exampleBlock, Item.Properties())
    Items.registerItem(
        ResourceLocation("yakclient", "example_block"),
        blockItem
    )
    val shaper = Minecraft.getInstance().itemRenderer.itemModelShaper
    shaper.register(
        blockItem,
        ModelResourceLocation("yakclient", "example_block", "inventory")
    )

    shaper.rebuildCache()
}

//@Mixin("net.minecraft.world.item.Items")
//abstract class RegisterItemsMixin {
//    @SourceInjection(
//        point = BEFORE_END,
//        from = "net.yakclient.extensions.example.RegisterItemsMixin",
//        to = "net.minecraft.world.item.Items",
//        methodFrom = "registerItem()V",
//        methodTo = "<clinit>()V",
//        priority = 0
//    )
//    fun registerItem() {
//
//    }
//}