package org.cubixmc.server.nbt;

import org.cubixmc.server.CubixServer;

import java.io.IOException;
import java.util.logging.Level;

public enum NBTType {
    BYTE(1, ByteTag.class),
    SHORT(2, ShortTag.class),
    INT(3, IntTag.class),
    LONG(4, LongTag.class),
    FLOAT(5, FloatTag.class),
    DOUBLE(6, DoubleTag.class),
    BYTE_ARRAY(7, ByteArrayTag.class),
    STRING(8, StringTag.class),
    LIST(9, ListTag.class),
    COMPOUND(10, CompoundTag.class),
    INT_ARRAY(11, null),
    END(0, null);

    private final int typeId;
    private final Class<? extends NBTTag> tagClass;

    private NBTType(int typeId, Class<? extends NBTTag> tagClass) {
        this.typeId = typeId;
        this.tagClass = tagClass;
    }

    public int getTypeId() {
        return typeId;
    }

    public NBTTag construct() {
        try {
            return tagClass.newInstance();
        } catch(Exception e) {
            CubixServer.getLogger().log(Level.SEVERE, "Failed to construct nbt tag with type " + toString(), e);
            return null;
        }
    }

    public static NBTType byTypeId(int id) {
        NBTType type = null;
        for(NBTType value : values()) {
            if(value.typeId == id) {
                type = value;
                break;
            }
        }

        return type;
    }
}
