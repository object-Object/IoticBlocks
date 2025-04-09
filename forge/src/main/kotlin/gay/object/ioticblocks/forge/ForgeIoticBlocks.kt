package gay.object.ioticblocks.forge

import dev.architectury.platform.forge.EventBuses
import gay.object.ioticblocks.IoticBlocks
import gay.object.ioticblocks.forge.datagen.IoticBlocksModels
import gay.object.ioticblocks.forge.datagen.IoticBlocksRecipes
import net.minecraft.data.DataProvider
import net.minecraft.data.DataProvider.Factory
import net.minecraft.data.PackOutput
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS

/**
 * This is your loading entrypoint on forge, in case you need to initialize
 * something platform-specific.
 */
@Mod(IoticBlocks.MODID)
class IoticBlocksForge {
    init {
        MOD_BUS.apply {
            EventBuses.registerModEventBus(IoticBlocks.MODID, this)
            addListener(ForgeIoticBlocksClient::init)
            addListener(::gatherData)
        }
        IoticBlocks.init()
    }

    private fun gatherData(event: GatherDataEvent) {
        event.apply {
            val efh = existingFileHelper
            addProvider(includeClient()) { IoticBlocksModels(it, efh) }
            addProvider(includeServer()) { IoticBlocksRecipes(it) }
        }
    }
}

fun <T : DataProvider> GatherDataEvent.addProvider(run: Boolean, factory: (PackOutput) -> T) =
    generator.addProvider(run, Factory { factory(it) })
