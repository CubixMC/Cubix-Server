package org.cubixmc.server.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ByteArrayTag extends NBTTag {
    private byte[] value;

    public ByteArrayTag(byte[] value) {
        this();
        this.value = value;
    }

    protected ByteArrayTag() {
        super(NBTType.BYTE_ARRAY);
    }

    @Override
    public void decode(DataInput input) throws IOException {
        int length = input.readInt();
        this.value = new byte[length];
        input.readFully(value);
    }

    @Override
    public void encode(DataOutput output) throws IOException {
        output.writeInt(value.length);
        output.write(value);
    }

    @Override
    public Object rawValue() {
        return value;
    }

    public byte[] value() {
        return value;
    }
}
