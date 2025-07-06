package com.kaolinmc.extensions.example

import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.server.Bootstrap
import net.minecraft.sounds.SoundEvents
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class MyCustomItem(properties: Properties) : Item(properties) {
    override fun getName(stack: ItemStack): Component {
        return Component.literal("Custom Item")
    }

    override fun use(level: Level, player: Player, hand: InteractionHand): InteractionResult {
        player.playSound(SoundEvents.BELL_RESONATE)
        Bootstrap.realStdoutPrintln("They used my item!")
        return super.use(level, player, hand)
    }
}

class MyCustomBlock(properties: Properties) : Block(properties) {
    override fun getName(): MutableComponent {
        return Component.literal("Custom Block")
    }

    override fun useItemOn(
        `$$0`: ItemStack,
        `$$1`: BlockState,
        `$$2`: Level,
        `$$3`: BlockPos,
        `$$4`: Player,
        `$$5`: InteractionHand,
        `$$6`: BlockHitResult
    ): InteractionResult {
        Bootstrap.realStdoutPrintln("They used my block!")
        return super.useItemOn(`$$0`, `$$1`, `$$2`, `$$3`, `$$4`, `$$5`, `$$6`)
    }
}

class SantaClausEffect : MobEffect(MobEffectCategory.BENEFICIAL, 0xe9b8b3) {
    fun applyUpdateEffect(entity: LivingEntity, amplifier: Int) {
        if (entity is Player) {
            entity.giveExperienceLevels(1 shl amplifier) // Higher amplifier gives you experience faster
        }
    }
}