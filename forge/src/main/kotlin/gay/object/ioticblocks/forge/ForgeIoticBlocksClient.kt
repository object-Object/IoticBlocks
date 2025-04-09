package gay.object.ioticblocks.forge

import gay.object.ioticblocks.IoticBlocksClient
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import thedarkcolour.kotlinforforge.forge.LOADING_CONTEXT

object ForgeIoticBlocksClient {
    fun init(event: FMLClientSetupEvent) {
        IoticBlocksClient.init()
    }
}
