package org.cubixmc.server.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class IntTag extends NBTTag {
    private int value;

    public IntTag(int value) {
        this();
        this.value = value;
    }

    protected IntTag() {
        super(NBTType.INT);
    }

    @Override
    public void decode(DataInput input) throws IOException {
        this.value = input.readInt();
    }

    @Override
    public void encode(DataOutput output) throws IOException {
        output.writeInt(value);
    }

    @Override
    public Object rawValue() {
        return value;
    }

    public int value() {
        return value;
    }
}
