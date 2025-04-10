package gay.`object`.ioticblocks.casting.actions.mixin

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getBlockPos
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadBlock
import at.petrak.hexcasting.common.casting.actions.rw.OpTheCoolerRead
import gay.`object`.ioticblocks.api.IoticBlocksAPI

object OpReadBlock {
    val argc: Int by OpTheCoolerRead::argc

    @JvmStatic
    fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val target = args.getBlockPos(0, argc)

        val datumHolder = IoticBlocksAPI.INSTANCE.findIotaHolder(env.world, target)
            ?: throw MishapBadBlock.of(target, "iota.read")

        val datum = datumHolder.readIota(env.world)
            ?: datumHolder.emptyIota()
            ?: throw MishapBadBlock.of(target, "iota.read")

        return listOf(datum)
    }
}
