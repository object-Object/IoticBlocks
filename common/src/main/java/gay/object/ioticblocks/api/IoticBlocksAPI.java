package gay.object.ioticblocks.api;

import at.petrak.hexcasting.api.addldata.ADIotaHolder;
import gay.object.ioticblocks.impl.IoticBlocksAPIImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IoticBlocksAPI {
    @NotNull
    IoticBlocksAPI INSTANCE = new IoticBlocksAPIImpl();

    /**
     * Attempts to find an {@link ADIotaHolder} instance for a block, in the following order of precedence:
     * <ul>
     *     <li>{@link ADIotaHolder} implemented on the block</li>
     *     <li>{@link IotaHolderProvider} implemented on the block</li>
     *     <li>{@link ADIotaHolder} implemented on the block entity</li>
     *     <li>{@link IotaHolderProvider} implemented on the block entity</li>
     *     <li>{@link IotaHolderProvider} registered for the block's id via {@link IoticBlocksAPI#registerIotaHolderProvider}</li>
     * </ul>
     * This order may change in the future. You should try not to rely on it; ideally, implement
     * only one of these options.
     */
    @Nullable
    ADIotaHolder findIotaHolder(@NotNull ServerLevel level, @NotNull BlockPos pos);

    /**
     * Registers an iota holder provider for a block to be used with IoticBlocks' read/write block patterns.
     * <p>
     * NOTE: you should implement {@link IotaHolderProvider} on the block or {@link ADIotaHolder} on
     * the block entity if possible.
     * This method should only be used in cases where that's not feasible, such as an optional hex
     * dependency or adding a provider to another mod's block.
     * </p>
     * @param id the resloc/id for the block (eg {@code minecraft:stone})
     * @return if the holder was successfully registered
     * @see IoticBlocksAPI#findIotaHolder(ServerLevel, BlockPos)
     */
    boolean registerIotaHolderProvider(@NotNull ResourceLocation id, @NotNull IotaHolderProvider provider);
}
