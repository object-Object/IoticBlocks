package gay.`object`.ioticblocks.fabric

import gay.`object`.ioticblocks.IoticBlocksClient
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType.CLIENT
import net.fabricmc.api.Environment

@Environment(CLIENT)
object FabricIoticBlocksClient : ClientModInitializer {
    override fun onInitializeClient() {
        IoticBlocksClient.init()
    }
}
