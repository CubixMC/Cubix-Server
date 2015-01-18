package org.cubixmc.server.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class DoubleTag extends NBTTag {
    private double value;

    public DoubleTag(double value) {
        this();
        this.value = value;
    }

    protected DoubleTag() {
        super(NBTType.DOUBLE);
    }

    @Override
    public void decode(DataInput input) throws IOException {
        this.value = input.readDouble();
    }

    @Override
    public void encode(DataOutput output) throws IOException {
        output.writeDouble(value);
    }

    @Override
    public Object rawValue() {
        return value;
    }

    public double value() {
        return value;
    }
}
