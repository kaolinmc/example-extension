@file:ImplementFeatures

package dev.extframework.extensions.example

import dev.extframework.core.api.feature.Feature
import dev.extframework.core.api.feature.ImplementFeatures
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.Items
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour

//val myBlock = Block(FabricBlockSettings.create().strength(4.0f))

@Feature
fun asdf(int: Int?) {
    println("Printing an int: '$int" + Block::class.java)
}

@Feature
fun registerBlock() {
    println("Hey um")

    val exampleBlock = MyCustomBlock(
        BlockBehaviour.Properties.of(),
//        FabricBlockSettings.create().strength(4.0f).noOcclusion()
    )

    Items.registerItem(
        ResourceLocation.parse("yakclient:custom_item"),
        MyCustomItem(Item.Properties())
    )

    val blockItem = BlockItem(exampleBlock, Item.Properties())

    Items.registerItem(
        ResourceLocation.parse("yakclient:example_block"),
        blockItem
    )

//    val shaper = Minecraft.getInstance().itemRenderer.itemModelShaper
//    shaper.register(
//        blockItem,
//        ModelResourceLocation("yakclient", "example_block", "inventory")
//    )
    Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.parse("yakclient:example_block"), exampleBlock)

}