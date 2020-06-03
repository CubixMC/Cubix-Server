package org.cubixmc.server.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListTag extends NBTTag {
    private List<NBTTag> value = new ArrayList<>();
    private NBTType innerType;

    public ListTag(NBTType innerType) {
        this();
        this.innerType = innerType;
    }

    protected ListTag() {
        super(NBTType.LIST);
    }

    @Override
    public void decode(DataInput input) throws IOException {
        this.innerType = NBTType.byTypeId(input.readByte());
        int length = input.readInt();
        for(int i = 0; i < length; i++) {
            NBTTag tag = innerType.construct();
            tag.decode(input);
            value.add(tag);
        }
    }

    @Override
    public void encode(DataOutput output) throws IOException {
        output.writeByte(innerType.getTypeId());
        output.writeInt(value.size());
        for(NBTTag tag : value) {
            tag.encode(output);
        }
    }

    @Override
    public Object rawValue() {
        return value;
    }

    public NBTType getInnerType() {
        return innerType;
    }

    public List<NBTTag> list() {
        return Collections.unmodifiableList(value);
    }

    public void addTag(NBTTag tag) {
        if(tag.getType() != innerType) {
            throw new IllegalArgumentException("Tag type does not equal to inner type!");
        }

        value.add(tag);
    }

    public NBTTag getTag(int index) {
        return value.get(index);
    }

    public boolean removeTag(NBTTag tag) {
        return value.remove(tag);
    }

    public void removeTag(int index) {
        value.remove(index);
    }

    public void clear() {
        value.clear();
    }

    public int size() {
        return value.size();
    }

    public boolean isEmpty() {
        return value.isEmpty();
    }
}
