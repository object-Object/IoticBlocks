@file:JvmName("IoticBlocksAbstractionsImpl")

package gay.object.ioticblocks.fabric

import gay.object.ioticblocks.registry.HexDebugRegistrar
import net.minecraft.core.Registry

fun <T : Any> initRegistry(registrar: HexDebugRegistrar<T>) {
    val registry = registrar.registry
    registrar.init { id, value -> Registry.register(registry, id, value) }
}
