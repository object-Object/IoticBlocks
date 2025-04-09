package gay.object.ioticblocks.forge.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import gay.object.ioticblocks.IoticBlocks;
import org.spongepowered.asm.mixin.Mixin;

// scuffed workaround for https://github.com/architectury/architectury-loom/issues/189
@Mixin(net.minecraft.data.Main.class)
public class MixinDatagenMain {
    @WrapMethod(method = "main")
    private static void ioticblocks$systemExitAfterDatagenFinishes(String[] strings, Operation<Void> original) {
        try {
            original.call((Object[]) strings);
        } catch (Throwable throwable) {
            IoticBlocks.LOGGER.error("Datagen failed!", throwable);
            System.exit(1);
        }
        IoticBlocks.LOGGER.info("Terminating datagen.");
        System.exit(0);
    }
}
