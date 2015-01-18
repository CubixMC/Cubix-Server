package org.cubixmc.server.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ShortTag extends NBTTag {
    private short value;

    public ShortTag(short value) {
        this();
        this.value = value;
    }

    protected ShortTag() {
        super(NBTType.SHORT);
    }

    @Override
    public void decode(DataInput input) throws IOException {
        this.value = input.readShort();
    }

    @Override
    public void encode(DataOutput output) throws IOException {
        output.writeShort(value);
    }

    @Override
    public Object rawValue() {
        return value;
    }

    public short value() {
        return value;
    }
}
