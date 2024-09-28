package dev.extframework.extensions.example

import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.Bootstrap
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour


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
    override fun getName(): MutableComponent {
        return Component.literal("Custom Block")
    }
}


class SantaClausEffect : MobEffect(MobEffectCategory.BENEFICIAL, 0xe9b8b3) {
    fun applyUpdateEffect(entity: LivingEntity, amplifier: Int) {
        if (entity is Player) {
            entity.giveExperienceLevels(1 shl amplifier) // Higher amplifier gives you experience faster
        }
    }
}



fun attemptRegister() {
//    val effect = SantaClausEffect()
//
//    Registry.register(BuiltInRegistries.MOB_EFFECT, ResourceLocation("yakclient", "santa_claus"), effect)
//    val TATER_POTION: Potion = Registry.register(
//        BuiltInRegistries.POTION,
//        ResourceLocation("yakclient", "santa_claus"),
//        Potion(
//            MobEffectInstance(
//                effect,
//                3600,
//                0
//            )
//        )
//    )



}

//@Mixin("net.minecraft.world.item.Items")
//abstract class RegisterItemsMixin {
//    @SourceInjection(
//        point = BEFORE_END,
//        from = "dev.extframework.extensions.example.RegisterItemsMixin",
//        to = "net.minecraft.world.item.Items",
//        methodFrom = "registerItem()V",
//        methodTo = "<clinit>()V",
//        priority = 0
//    )
//    fun registerItem() {
//
//    }
//}