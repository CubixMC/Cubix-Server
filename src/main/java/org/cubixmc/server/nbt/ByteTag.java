package org.cubixmc.server.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ByteTag extends NBTTag {
    private byte value;

    public ByteTag(byte value) {
        this();
        this.value = value;
    }

    protected ByteTag() {
        super(NBTType.BYTE);
    }

    @Override
    public void decode(DataInput input) throws IOException {
        this.value = input.readByte();
    }

    @Override
    public void encode(DataOutput output) throws IOException {
        output.writeByte(value);
    }

    @Override
    public Object rawValue() {
        return value;
    }

    public byte value() {
        return value;
    }
}
