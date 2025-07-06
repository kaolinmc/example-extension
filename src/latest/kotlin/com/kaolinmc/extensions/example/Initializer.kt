package com.kaolinmc.extensions.example

import com.kaolinmc.core.entrypoint.Entrypoint
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.Bootstrap
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour

class Initializer : Entrypoint() {
    override fun init() {
        ExampleExtension.registerBlock += {
            val blockKey: ResourceKey<Block> =
                ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("example-extension", "example_block"))

            val exampleBlock = MyCustomBlock(
                BlockBehaviour.Properties.of()
                    .setId(blockKey),
            )
            Registry.register(BuiltInRegistries.BLOCK, blockKey, exampleBlock)

            val blockItemKey: ResourceKey<Item> =
                ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("example-extension", "example_block"))

            Registry.register(
                BuiltInRegistries.ITEM,
                blockItemKey,
                BlockItem(exampleBlock, Item.Properties().setId(blockItemKey))
            )
        }
    }
}