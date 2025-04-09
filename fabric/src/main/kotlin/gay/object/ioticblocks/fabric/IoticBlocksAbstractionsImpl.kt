@file:JvmName("IoticBlocksAbstractionsImpl")

package gay.`object`.ioticblocks.fabric

import gay.`object`.ioticblocks.registry.IoticBlocksRegistrar
import net.minecraft.core.Registry

fun <T : Any> initRegistry(registrar: IoticBlocksRegistrar<T>) {
    val registry = registrar.registry
    registrar.init { id, value -> Registry.register(registry, id, value) }
}
