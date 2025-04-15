package gay.`object`.ioticblocks.registry

import at.petrak.hexcasting.api.casting.ActionRegistryEntry
import at.petrak.hexcasting.api.casting.castables.Action
import at.petrak.hexcasting.api.casting.math.HexDir
import at.petrak.hexcasting.api.casting.math.HexPattern
import at.petrak.hexcasting.common.lib.HexRegistries
import at.petrak.hexcasting.common.lib.hex.HexActions
import gay.`object`.ioticblocks.casting.actions.OpReadIndex
import gay.`object`.ioticblocks.casting.actions.OpWriteIndex

object IoticBlocksActions : IoticBlocksRegistrar<ActionRegistryEntry>(
    HexRegistries.ACTION,
    { HexActions.REGISTRY },
) {
    val READ_INDEX = make("read/index", HexDir.EAST, "aqqqqqedwewewewdw", OpReadIndex)
    val WRITE_INDEX = make("write/index", HexDir.EAST, "deeeeeqawqwaw", OpWriteIndex)

    private fun make(name: String, startDir: HexDir, signature: String, action: Action) =
        make(name, startDir, signature) { action }

    private fun make(name: String, startDir: HexDir, signature: String, getAction: () -> Action) = register(name) {
        ActionRegistryEntry(HexPattern.fromAngles(signature, startDir), getAction())
    }
}