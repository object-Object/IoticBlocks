@file:JvmName("IoticBlocksAbstractionsImpl")

package gay.`object`.ioticblocks.forge

import gay.`object`.ioticblocks.registry.IoticBlocksRegistrar
import net.minecraftforge.registries.RegisterEvent
import thedarkcolour.kotlinforforge.forge.MOD_BUS

fun <T : Any> initRegistry(registrar: IoticBlocksRegistrar<T>) {
    MOD_BUS.addListener { event: RegisterEvent ->
        event.register(registrar.registryKey) { helper ->
            registrar.init(helper::register)
        }
    }
}
