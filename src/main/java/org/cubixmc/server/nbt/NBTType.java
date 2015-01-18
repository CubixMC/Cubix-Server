package org.cubixmc.server.nbt;

import org.cubixmc.server.CubixServer;

import java.io.IOException;
import java.util.logging.Level;

public enum NBTType {
    BYTE(1, null),
    SHORT(2, null),
    INT(3, null),
    LONG(4, null),
    FLOAT(5, null),
    DOUBLE(6, null),
    BYTE_ARRAY(7, null),
    STRING(8, null),
    LIST(9, null),
    COMPOUND(10, null),
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
            CubixServer.getLogger().log(Level.SEVERE, "Failed to construct nbt tag", e);
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
