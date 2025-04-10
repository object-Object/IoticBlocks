package gay.object.ioticblocks.mixin;

import at.petrak.hexcasting.api.casting.castables.SpellAction;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.common.casting.actions.rw.OpTheCoolerWrite;
import gay.object.ioticblocks.casting.actions.mixin.OpWriteBlock;
import gay.object.ioticblocks.utils.ActionUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(OpTheCoolerWrite.class)
public class MixinOpTheCoolerWrite {
    @Final
    @Shadow(remap = false)
    private static int argc;

    @Inject(method = "execute", at = @At("HEAD"), cancellable = true, remap = false)
    private void redirectWriteBlock(
        List<? extends Iota> args,
        CastingEnvironment env,
        CallbackInfoReturnable<SpellAction.Result> cir
    ) {
        ActionUtils.getEntityOrBlockPos(args, 0, argc).ifRight(blockPos ->
            cir.setReturnValue(OpWriteBlock.execute(args, env))
        );
    }
}
