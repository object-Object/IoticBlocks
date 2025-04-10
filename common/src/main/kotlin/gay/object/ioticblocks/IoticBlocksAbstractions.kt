@file:JvmName("IoticBlocksAbstractions")

package gay.`object`.ioticblocks

import dev.architectury.injectables.annotations.ExpectPlatform
import gay.`object`.ioticblocks.registry.IoticBlocksRegistrar

fun initRegistries(vararg registries: IoticBlocksRegistrar<*>) {
    for (registry in registries) {
        initRegistry(registry)
    }
}

@ExpectPlatform
fun <T : Any> initRegistry(registrar: IoticBlocksRegistrar<T>) {
    throw AssertionError()
}
