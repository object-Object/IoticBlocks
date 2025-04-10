package gay.`object`.ioticblocks

import gay.`object`.ioticblocks.registry.IoticBlocksActions
import net.minecraft.resources.ResourceLocation
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


object IoticBlocks {
    const val MODID = "ioticblocks"

    @JvmField
    val LOGGER: Logger = LogManager.getLogger(MODID)

    @JvmStatic
    fun id(path: String) = ResourceLocation(MODID, path)

    fun init() {
        LOGGER.info("Putting chemicals in the water to turn the blocks iotic...")
        initRegistries(
            IoticBlocksActions,
        )
    }
}
