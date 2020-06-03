package org.cubixmc.server.nbt;

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
        this.value = input.readUTF();
    }

    @Override
    public void encode(DataOutput output) throws IOException {
        output.writeUTF(value);
    }

    @Override
    public Object rawValue() {
        return value;
    }

    public String value() {
        return value;
    }
}
