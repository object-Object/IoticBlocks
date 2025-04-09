package gay.`object`.ioticblocks.forge

import gay.`object`.ioticblocks.IoticBlocksClient
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent

object ForgeIoticBlocksClient {
    fun init(event: FMLClientSetupEvent) {
        IoticBlocksClient.init()
    }
}
