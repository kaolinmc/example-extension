package net.yakclient.extensions.example

import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.Bootstrap
import net.minecraft.sounds.SoundEvents
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
        Items.registerItem(
            ResourceLocation("yakclient", "custom_item"),
            MyCustomItem(Item.Properties())
        )
    }
}