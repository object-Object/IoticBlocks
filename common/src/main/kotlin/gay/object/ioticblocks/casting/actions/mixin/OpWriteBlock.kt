package gay.`object`.ioticblocks.casting.actions.mixin

import at.petrak.hexcasting.api.addldata.ADIotaHolder
import at.petrak.hexcasting.api.casting.ParticleSpray
import at.petrak.hexcasting.api.casting.RenderedSpell
import at.petrak.hexcasting.api.casting.castables.SpellAction
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment
import at.petrak.hexcasting.api.casting.getBlockPos
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.mishaps.MishapBadBlock
import at.petrak.hexcasting.api.casting.mishaps.MishapOthersName
import at.petrak.hexcasting.common.casting.actions.rw.OpTheCoolerWrite
import gay.`object`.ioticblocks.api.IoticBlocksAPI
import net.minecraft.world.phys.Vec3

object OpWriteBlock {
    val argc: Int by OpTheCoolerWrite::argc

    @JvmStatic
    fun execute(args: List<Iota>, env: CastingEnvironment): SpellAction.Result {
        val target = args.getBlockPos(0, argc)
        val datum = args[1]

        env.assertPosInRangeForEditing(target)

        val datumHolder = IoticBlocksAPI.INSTANCE.findIotaHolder(env.world, target)
            ?: throw MishapBadBlock.of(target, "iota.write")

        if (!datumHolder.writeIota(datum, true)) {
            throw MishapBadBlock.of(target, "iota.write")
        }

        val trueName = MishapOthersName.getTrueNameFromDatum(datum, null)
        if (null != trueName) {
            throw MishapOthersName(trueName)
        }

        return SpellAction.Result(
            Spell(datum, datumHolder),
            0,
            listOf(ParticleSpray(target.center, Vec3(1.0, 0.0, 0.0), 0.25, 3.14, 40))
        )
    }

    private data class Spell(val datum: Iota, val datumHolder: ADIotaHolder) : RenderedSpell {
        override fun cast(env: CastingEnvironment) {
            datumHolder.writeIota(datum, false)
        }
    }
}
