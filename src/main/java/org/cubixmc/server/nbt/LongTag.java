package org.cubixmc.server.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class LongTag extends NBTTag {
    private long value;

    public LongTag(long value) {
        this();
        this.value = value;
    }

    protected LongTag() {
        super(NBTType.LONG);
    }

    @Override
    public void decode(DataInput input) throws IOException {
        this.value = input.readLong();
    }

    @Override
    public void encode(DataOutput output) throws IOException {
        output.writeLong(value);
    }

    @Override
    public Object rawValue() {
        return value;
    }

    public long value() {
        return value;
    }
}
