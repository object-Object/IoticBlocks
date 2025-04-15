package gay.`object`.ioticblocks.casting.actions

import at.petrak.hexcasting.api.casting.castables.ConstMediaAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getDouble
import at.petrak.hexcasting.api.casting.iota.GarbageIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.IotaType
import at.petrak.hexcasting.api.casting.iota.NullIota
import at.petrak.hexcasting.api.utils.downcast
import at.petrak.hexcasting.common.lib.hex.HexIotaTypes
import at.petrak.hexcasting.xplat.IXplatAbstractions
import gay.`object`.ioticblocks.IoticBlocks
import gay.`object`.ioticblocks.api.IoticBlocksAPI
import gay.`object`.ioticblocks.utils.getEntityOrBlockPos
import gay.`object`.ioticblocks.utils.mishapBadEntityOrBlock
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import kotlin.math.roundToInt

object OpReadIndex : ConstMediaAction {
    override val argc = 2

    override fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val target = args.getEntityOrBlockPos(0, argc)
        val index = args.getDouble(1, argc).roundToInt()

        target.map(env::assertEntityInRange, env::assertPosInRange)

        val datumHolder = target.map(
            IXplatAbstractions.INSTANCE::findDataHolder,
            { IoticBlocksAPI.INSTANCE.findIotaHolder(env.world, it) },
        ) ?: throw mishapBadEntityOrBlock(target, "iota.read")

        // read/deserialize the list manually so we avoid deserializing data we don't need
        val tag = datumHolder.readIotaTag()
            ?: throw mishapBadEntityOrBlock(target, "iota.read")

        if (tag.getString(HexIotaTypes.KEY_TYPE) != "hexcasting:list") {
            throw mishapBadEntityOrBlock(target, "iota.read.list")
        }

        val data = tag.get(HexIotaTypes.KEY_DATA)
            ?: throw mishapBadEntityOrBlock(target, "iota.read.list")

        val listTag = try {
            data.downcast(ListTag.TYPE)
        } catch (e: IllegalArgumentException) {
            IoticBlocks.LOGGER.warn("Caught an exception deserializing a list iota", e)
            throw mishapBadEntityOrBlock(target, "iota.read.list")
        }

        // return null if the target contains a valid list but the index is out of range
        val datumTag = listTag.getOrNull(index)
            ?: return listOf(NullIota())

        val datum = try {
            val ctag = datumTag.downcast(CompoundTag.TYPE)
            IotaType.deserialize(ctag, env.world)
        } catch (e: IllegalArgumentException) {
            IoticBlocks.LOGGER.warn("Caught an exception deserializing an iota at index $index", e)
            GarbageIota()
        }

        return listOf(datum)
    }
}