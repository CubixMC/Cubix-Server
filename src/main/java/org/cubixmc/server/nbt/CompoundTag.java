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
        this();
        this.name = name;
    }

    protected CompoundTag() {
        super(NBTType.COMPOUND);
    }

    @Override
    public void decode(DataInput input) throws IOException {
        NBTType type;
        while((type = NBTType.byTypeId(input.readByte())) != NBTType.END) {
            // Read tag name
            int length = input.readInt();
            byte[] bytes = new byte[length];
            input.readFully(bytes);
            String tagName = new String(bytes, Charsets.UTF_8);

            // Make tag
            NBTTag tag = type.construct();
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
            output.writeShort(tagName.length());
            output.write(tagName.getBytes(Charsets.UTF_8));

            // Write tag data
            tag.encode(output);
        }
    }

    @Override
    public Object rawValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
