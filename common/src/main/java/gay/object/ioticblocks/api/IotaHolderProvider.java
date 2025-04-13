package gay.object.ioticblocks.api;

import at.petrak.hexcasting.api.addldata.ADIotaHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface IotaHolderProvider {
    /**
     * Returns an {@link ADIotaHolder} instance for the block at the given position, or null if the
     * block is not of the expected type.
     * <br>
     * The value returned by this method should be considered ephemeral, ie. it shouldn't be cached
     * because it may return an invalid result later.
     * @see IoticBlocksAPI#registerIotaHolderProvider
     */
    @Nullable
    ADIotaHolder getIotaHolder(@NotNull ServerLevel level, @NotNull BlockPos pos);
}
