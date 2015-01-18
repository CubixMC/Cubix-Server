package org.cubixmc.server.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class FloatTag extends NBTTag {
    private float value;

    public FloatTag(float value) {
        this();
        this.value = value;
    }

    protected FloatTag() {
        super(NBTType.FLOAT);
    }

    @Override
    public void decode(DataInput input) throws IOException {
        this.value = input.readFloat();
    }

    @Override
    public void encode(DataOutput output) throws IOException {
        output.writeFloat(value);
    }

    @Override
    public Object rawValue() {
        return value;
    }

    public float value() {
        return value;
    }
}
