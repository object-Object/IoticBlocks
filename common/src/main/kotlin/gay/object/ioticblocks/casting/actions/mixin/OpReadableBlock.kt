package gay.`object`.ioticblocks.casting.actions.mixin

import at.petrak.hexcasting.api.casting.asActionResult
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getBlockPos
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.common.casting.actions.rw.OpTheCoolerReadable
import gay.`object`.ioticblocks.api.IoticBlocksAPI

object OpReadableBlock {
    val argc: Int by OpTheCoolerReadable::argc

    @JvmStatic
    fun execute(args: List<Iota>, env: CastingEnvironment): List<Iota> {
        val target = args.getBlockPos(0, argc)
        env.assertPosInRange(target)

        val datumHolder = IoticBlocksAPI.INSTANCE.findIotaHolder(env.world, target)
            ?: return false.asActionResult

        datumHolder.readIota(env.world)
            ?: datumHolder.emptyIota()
            ?: return false.asActionResult

        return true.asActionResult
    }
}
