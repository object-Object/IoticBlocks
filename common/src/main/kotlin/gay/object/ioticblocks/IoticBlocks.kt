package gay.`object`.ioticblocks

import at.petrak.hexcasting.api.HexAPI
import at.petrak.hexcasting.api.addldata.ADIotaHolder
import at.petrak.hexcasting.api.casting.iota.BooleanIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.casting.iota.ListIota
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.api.casting.iota.PatternIota
import at.petrak.hexcasting.common.blocks.akashic.AkashicFloodfiller
import at.petrak.hexcasting.common.blocks.akashic.BlockEntityAkashicBookshelf
import at.petrak.hexcasting.common.blocks.circles.BlockEntitySlate
import at.petrak.hexcasting.common.blocks.circles.BlockSlate
import gay.`object`.ioticblocks.api.ADIotaHolderReadOnly
import gay.`object`.ioticblocks.api.IotaHolderProvider
import gay.`object`.ioticblocks.api.IoticBlocksAPI
import gay.`object`.ioticblocks.registry.IoticBlocksActions
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.level.ServerLevel
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
        registerIotaHolderProviders()
    }

    private fun registerIotaHolderProviders() {
        registerHexIotaHolderProvider("akashic_record") { level, recordPos ->
            val labels = mutableListOf<Iota>()
            AkashicFloodfiller.floodFillFor(recordPos, level) { pos, _, _ ->
                (level.getBlockEntity(pos) as? BlockEntityAkashicBookshelf)?.pattern?.let {
                    labels.add(PatternIota(it))
                }
                false
            }
            ADIotaHolderReadOnly.ofStatic(ListIota(labels))
        }

        registerHexIotaHolderProvider("akashic_bookshelf") { level, pos ->
            val patternIota = (level.getBlockEntity(pos) as? BlockEntityAkashicBookshelf)
                ?.pattern
                ?.let(::PatternIota)
            ADIotaHolderReadOnly.ofStatic(patternIota)
        }

        registerHexIotaHolderProvider("slate") { level, pos ->
            val bs = level.getBlockState(pos)
            val block = (bs.block as? BlockSlate)
                ?: return@registerHexIotaHolderProvider null
            val tile = (level.getBlockEntity(pos) as? BlockEntitySlate)
                ?: return@registerHexIotaHolderProvider null

            object : ADIotaHolder {
                override fun readIotaTag(): CompoundTag? {
                    return tile.pattern?.let(::PatternIota)?.let { IotaType.serialize(it) }
                }

                override fun readIota(world: ServerLevel): Iota? {
                    return tile.pattern?.let(::PatternIota)
                }

                override fun writeIota(iota: Iota?, simulate: Boolean): Boolean {
                    if (!writeable()) return false

                    val pattern = when (iota) {
                        is PatternIota -> iota.pattern
                        is NullIota, null -> null
                        else -> return false
                    }

                    if (!simulate) {
                        tile.pattern = pattern
                        tile.sync()
                    }

                    return true
                }

                override fun writeable(): Boolean {
                    return !block.isEnergized(pos, bs, level)
                }
            }
        }

        IoticBlocksAPI.INSTANCE.registerIotaHolderProvider(ResourceLocation("minecraft", "cake")) { _, _ ->
            ADIotaHolderReadOnly.ofStatic(BooleanIota(false))
        }
    }

    private fun registerHexIotaHolderProvider(name: String, provider: IotaHolderProvider) =
        IoticBlocksAPI.INSTANCE.registerIotaHolderProvider(HexAPI.modLoc(name), provider)
}
