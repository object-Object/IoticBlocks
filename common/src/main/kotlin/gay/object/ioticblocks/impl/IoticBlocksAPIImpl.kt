package gay.`object`.ioticblocks.impl

import at.petrak.hexcasting.api.addldata.ADIotaHolder
import gay.`object`.ioticblocks.IoticBlocks
import gay.`object`.ioticblocks.api.IotaHolderProvider
import gay.`object`.ioticblocks.api.IoticBlocksAPI
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
import java.util.concurrent.ConcurrentHashMap

/** Do not instantiate this class yourself! Use [IoticBlocksAPI.INSTANCE] instead. */
class IoticBlocksAPIImpl internal constructor() : IoticBlocksAPI {
    private val registeredIotaHolderProviders = ConcurrentHashMap<ResourceLocation, IotaHolderProvider>()

    override fun findIotaHolder(level: ServerLevel, pos: BlockPos): ADIotaHolder? {
        val block = level.getBlockState(pos).block
        if (block is ADIotaHolder) {
            return block
        }

        if (block is IotaHolderProvider) {
            return block.getIotaHolder(level, pos)
        }

        val blockEntity = level.getBlockEntity(pos)
        if (blockEntity != null) {
            if (blockEntity is ADIotaHolder) {
                return blockEntity
            }

            if (blockEntity is IotaHolderProvider) {
                return blockEntity.getIotaHolder(level, pos)
            }
        }

        val id = BuiltInRegistries.BLOCK.getKey(block)
        return registeredIotaHolderProviders[id]?.getIotaHolder(level, pos)
    }

    override fun registerIotaHolderProvider(id: ResourceLocation, provider: IotaHolderProvider): Boolean {
        if (registeredIotaHolderProviders.containsKey(id)) {
            IoticBlocks.LOGGER.warn("Attempted to register duplicate IotaHolderProvider for block: $id")
            return false
        }
        registeredIotaHolderProviders[id] = provider
        return true
    }
}
