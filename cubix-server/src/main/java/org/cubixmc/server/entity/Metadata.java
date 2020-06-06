package org.cubixmc.server.entity;

import org.bukkit.Location;
import org.cubixmc.inventory.ItemStack;
import org.cubixmc.server.network.Codec;
import org.cubixmc.util.Vector3I;

public class Metadata {
    private final Object[] objects = new Object[23];

    public void set(int id, Object object) {
        objects[id] = object;
    }

    public <T> T get(int id, Class<T> type) {
        return type.cast(objects[id]);
    }

    public void watch(int id, int mask, boolean value) {
        byte bitmask = (byte) objects[id];
        int current = bitmask >> mask;
        if(current == 1 && !value) {
            bitmask -= Math.pow(2, mask);
        } else if(current == 0 && value) {
            bitmask += Math.pow(2, mask);
        } else {
            return;
        }

        objects[id] = bitmask;
    }

    public void encode(Codec codec) {
        for(int i = 0; i < objects.length; i++) {
            Object object = objects[i];
            if(object == null) {
                continue;
            }

            switch(object.getClass().getSimpleName().toLowerCase()) {
                case "byte":
                    writeFlag(codec, 0, i);
                    codec.writeByte((byte) object);
                    break;
                case "short":
                    writeFlag(codec, 1, i);
                    codec.writeShort((byte) object);
                    break;
                case "integer":
                    writeFlag(codec, 2, i);
                    codec.writeInt((int) object);
                    break;
                case "float":
                    writeFlag(codec, 3, i);
                    codec.writeFloat((float) object);
                    break;
                case "string":
                    writeFlag(codec, 4, i);
                    codec.writeString((String) object);
                    break;
                case "itemstack":
                    writeFlag(codec, 5, i);
                    codec.writeSlot((ItemStack) object);
                    break;
                case "vector3i":
                case "location":
                    writeFlag(codec, 6, i);

                    Vector3I pos;
                    if(object instanceof Vector3I) {
                        pos = (Vector3I) object;
                    } else {
                        pos = new Vector3I((Location) object);
                    }

                    codec.writeInt(pos.getX());
                    codec.writeInt(pos.getY());
                    codec.writeInt(pos.getZ());
                    break;
                // TODO: Vector3F
                default:
                    throw new IllegalArgumentException("Unknown object " + object.getClass().getSimpleName());
            }
        }
        codec.writeByte(127);
    }

    private void writeFlag(Codec codec, int type, int index) {
        codec.writeByte((type << 5 | index & 0x1F) & 0xFF);
    }
}
