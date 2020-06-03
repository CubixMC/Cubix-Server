package org.cubixmc.server.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class IntArrayTag extends NBTTag {
    private int[] value;

    public IntArrayTag(int[] value) {
        this();
        this.value = value;
    }

    protected IntArrayTag() {
        super(NBTType.INT_ARRAY);
    }

    @Override
    public void decode(DataInput input) throws IOException {
        int length = input.readInt();
        this.value = new int[length];
        for(int i = 0; i < length; i++) {
            value[i] = input.readInt();
        }
    }

    @Override
    public void encode(DataOutput output) throws IOException {
        output.writeInt(value.length);
        for(int i = 0; i < value.length; i++) {
            output.writeInt(value[i]);
        }
    }

    @Override
    public Object rawValue() {
        return value;
    }

    public int[] value() {
        return value;
    }
}
