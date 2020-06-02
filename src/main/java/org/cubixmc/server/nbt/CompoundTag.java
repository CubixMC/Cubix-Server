package org.cubixmc.server.nbt;

import com.google.common.base.Charsets;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class CompoundTag extends NBTTag {
    private final Map<String, NBTTag> value = new HashMap<>();
    private String name;

    public CompoundTag(String name) {
        super(NBTType.COMPOUND);
        this.name = (name == null || name.isEmpty()) ? "root" : name;
    }

    public CompoundTag() {
        this(null);
    }

    @Override
    public void decode(DataInput input) throws IOException {
        NBTType type;
        while((type = NBTType.byTypeId(input.readByte())) != NBTType.END) {
            // Read tag name
            String tagName = input.readUTF();

            // Make tag
            NBTTag tag = type == NBTType.COMPOUND ? new CompoundTag(tagName) : type.construct();
            tag.decode(input);
            value.put(tagName, tag);
        }
    }

    @Override
    public void encode(DataOutput output) throws IOException {
        for(Entry<String, NBTTag> entry : value.entrySet()) {
            String tagName = entry.getKey();
            NBTTag tag = entry.getValue();

            // Write tag type
            output.writeByte(tag.getType().getTypeId());

            // Write tag name
//            output.writeUTF(tagName);
            output.writeShort(tagName.length());
            output.write(tagName.getBytes(Charsets.UTF_8));

            // Write tag data
            tag.encode(output);
        }
        output.writeByte(NBTType.END.getTypeId());
    }

    @Override
    public Object rawValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public boolean has(String key) {
        return value.containsKey(key);
    }

    public boolean hasWithType(String key, NBTType type) {
        NBTTag tag = value.get(key);
        return tag != null && tag.getType() == type;
    }

    public <T extends NBTTag> T getTag(String key, Class<T> type) throws NBTException {
        NBTTag tag = getTagUnsafe(key);
        if(tag == null) {
            throw new NBTException("Tag with name " + key + " does not exist!");
        } else if(!type.isInstance(tag)) {
            throw new NBTException("Tag with name " + key + " does not match the requested type!");
        }

        return type.cast(tag);
    }

    public <T extends NBTTag> T getTagUnsafe(String key, Class<T> type) {
        NBTTag tag = getTagUnsafe(key);
        return tag == null ? null : type.cast(tag);
    }

    public NBTTag getTagUnsafe(String key) {
        return value.get(key);
    }

    public void addTag(String key, NBTTag tag) {
        value.put(key, tag);
    }

    public boolean getBoolean(String key) throws NBTException {
        return getByte(key) != 0;
    }

    public byte getByte(String key) throws NBTException {
        try {
            return getTag(key, ByteTag.class).value();
        } catch(NBTException e) {
            return 0;
        }
    }

    public short getShort(String key) throws NBTException {
        try {
            return getTag(key, ShortTag.class).value();
        } catch(NBTException e) {
            return 0;
        }
    }

    public int getInt(String key) throws NBTException {
        try {
            return getTag(key, IntTag.class).value();
        } catch(NBTException e) {
            return 0;
        }
    }

    public float getFloat(String key) throws NBTException {
        try {
            return getTag(key, FloatTag.class).value();
        } catch(NBTException e) {
            return 0;
        }
    }

    public double getDouble(String key) throws NBTException {
        try {
            return getTag(key, DoubleTag.class).value();
        } catch(NBTException e) {
            return 0;
        }
    }

    public long getLong(String key) throws NBTException {
        try {
            return getTag(key, LongTag.class).value();
        } catch(NBTException e) {
            return 0;
        }
    }

    public String getString(String key) throws NBTException {
        return getTag(key, StringTag.class).value();
    }

    public byte[] getByteArray(String key) throws NBTException {
        return getTag(key, ByteArrayTag.class).value();
    }

    public int[] getIntArray(String key) throws NBTException {
        return getTag(key, IntArrayTag.class).value();
    }

    public ListTag getList(String key) throws NBTException {
        return getTag(key, ListTag.class);
    }

    public CompoundTag getCompound(String key) throws NBTException {
        return getTag(key, CompoundTag.class);
    }
}
