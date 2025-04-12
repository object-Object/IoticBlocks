package gay.object.ioticblocks.mixin;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.common.casting.actions.rw.OpTheCoolerReadable;
import gay.object.ioticblocks.casting.actions.mixin.OpReadableBlock;
import gay.object.ioticblocks.utils.IoticBlocksUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(OpTheCoolerReadable.class)
public abstract class MixinOpTheCoolerReadable {
    @Final
    @Shadow(remap = false)
    private static int argc;

    @Inject(method = "execute", at = @At("HEAD"), cancellable = true, remap = false)
    private void handleBlock(
        List<? extends Iota> args,
        CastingEnvironment env,
        CallbackInfoReturnable<List<Iota>> cir
    ) {
        IoticBlocksUtils.getEntityOrBlockPos(args, 0, argc).ifRight(blockPos ->
            cir.setReturnValue(OpReadableBlock.execute(args, env))
        );
    }
}
