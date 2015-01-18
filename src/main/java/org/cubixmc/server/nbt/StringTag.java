package org.cubixmc.server.nbt;

import com.google.common.base.Charsets;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class StringTag extends NBTTag {
    private String value;

    public StringTag(String value) {
        this();
        this.value = value;
    }

    protected StringTag() {
        super(NBTType.STRING);
    }

    @Override
    public void decode(DataInput input) throws IOException {
        int length = input.readShort();
        byte[] bytes = new byte[length];
        input.readFully(bytes);
        this.value = new String(bytes, Charsets.UTF_8);
    }

    @Override
    public void encode(DataOutput output) throws IOException {
        output.writeShort(value.length());
        output.write(value.getBytes(Charsets.UTF_8));
    }

    @Override
    public Object rawValue() {
        return value;
    }

    public String value() {
        return value;
    }
}
