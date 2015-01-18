package org.cubixmc.server.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class NBTTag {
    private final NBTType type;

    public NBTTag(NBTType type) {
        this.type = type;
    }

    public abstract void decode(DataInput input) throws IOException;

    public abstract void encode(DataOutput output) throws IOException;

    public abstract Object rawValue();

    public NBTType getType() {
        return type;
    }
}
