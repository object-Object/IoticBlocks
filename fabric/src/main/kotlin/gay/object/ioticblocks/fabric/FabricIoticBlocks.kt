package gay.`object`.ioticblocks.fabric

import gay.`object`.ioticblocks.IoticBlocks
import net.fabricmc.api.ModInitializer

object FabricIoticBlocks : ModInitializer {
    override fun onInitialize() {
        IoticBlocks.init()
    }
}
