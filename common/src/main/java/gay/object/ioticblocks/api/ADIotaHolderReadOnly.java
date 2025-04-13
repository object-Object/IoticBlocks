package gay.object.ioticblocks.api;

import at.petrak.hexcasting.api.addldata.ADIotaHolder;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** A helper interface extending {@link ADIotaHolder} for read-only iota holders. */
public interface ADIotaHolderReadOnly extends ADIotaHolder {
    @Override
    default boolean writeIota(@Nullable Iota iota, boolean simulate) {
        return false;
    }

    @Override
    default boolean writeable() {
        return false;
    }

    /** Wraps an iota in a read-only iota holder. */
    @NotNull
    static ADIotaHolder ofStatic(@Nullable Iota iota) {
        return new ADIotaHolderReadOnly() {
            @Override
            @Nullable
            public CompoundTag readIotaTag() {
                if (iota != null) {
                    return IotaType.serialize(iota);
                } else {
                    return null;
                }
            }

            @Override
            @Nullable
            public Iota readIota(ServerLevel world) {
                return iota;
            }
        };
    }
}
