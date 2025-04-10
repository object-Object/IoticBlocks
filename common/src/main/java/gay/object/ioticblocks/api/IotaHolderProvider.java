package gay.object.ioticblocks.api;

import at.petrak.hexcasting.api.addldata.ADIotaHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface IotaHolderProvider {
    @NotNull
    ADIotaHolder getIotaHolder(@NotNull ServerLevel level, @NotNull BlockPos pos);
}
