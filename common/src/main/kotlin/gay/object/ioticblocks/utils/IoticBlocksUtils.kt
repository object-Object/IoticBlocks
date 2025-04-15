@file:JvmName("IoticBlocksUtils")

package gay.`object`.ioticblocks.utils

import at.petrak.hexcasting.api.casting.iota.EntityIota
import at.petrak.hexcasting.api.casting.iota.Iota
import at.petrak.hexcasting.api.casting.iota.Vec3Iota
import at.petrak.hexcasting.api.casting.mishaps.*
import com.mojang.datafixers.util.Either
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.Entity

fun List<Iota>.getEntityOrBlockPos(idx: Int, argc: Int = 0): Either<Entity, BlockPos> {
    val datum = this.getOrElse(idx) { throw MishapNotEnoughArgs(idx + 1, this.size) }
    return when (datum) {
        is EntityIota -> Either.left(datum.entity)
        is Vec3Iota -> Either.right(BlockPos.containing(datum.vec3))
        else -> throw MishapInvalidIota.ofType(
            datum,
            if (argc == 0) idx else argc - (idx + 1),
            "entity_or_vector"
        )
    }
}

fun mishapBadEntityOrBlock(target: Either<Entity, BlockPos>, stub: String): Mishap {
    return target.map(
        { MishapBadEntity.of(it, stub) },
        { MishapBadBlock.of(it, stub) },
    )
}
