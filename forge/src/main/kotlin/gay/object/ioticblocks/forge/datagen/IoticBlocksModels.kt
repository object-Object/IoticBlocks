@file:OptIn(ExperimentalStdlibApi::class)

package gay.object.ioticblocks.forge.datagen

import gay.object.ioticblocks.IoticBlocks
import gay.object.ioticblocks.items.ItemDebugger
import gay.object.ioticblocks.items.ItemDebugger.DebugState
import gay.object.ioticblocks.items.ItemDebugger.StepMode
import gay.object.ioticblocks.items.ItemEvaluator
import gay.object.ioticblocks.items.ItemEvaluator.EvalState
import gay.object.ioticblocks.registry.IoticBlocksItems
import gay.object.ioticblocks.utils.itemPredicate
import net.minecraft.data.PackOutput
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.client.model.generators.ModelBuilder
import net.minecraftforge.client.model.generators.ModelFile
import net.minecraftforge.common.data.ExistingFileHelper

class IoticBlocksModels(output: PackOutput, efh: ExistingFileHelper) : ItemModelProvider(output, IoticBlocks.MODID, efh) {
    override fun registerModels() {
        basicItem(IoticBlocksItems.DUMMY_ITEM.id)
            .parent(ModelFile.UncheckedModelFile("item/handheld_rod"))
    }
}

// utility function for adding multiple possibly missing layers to a generated item model
fun <T : ModelBuilder<T>> T.layers(start: Int, vararg layers: String?): T {
    var index = start
    for (layer in layers) {
        if (layer != null) {
            texture("layer$index", layer)
            index += 1
        }
    }
    return this
}
