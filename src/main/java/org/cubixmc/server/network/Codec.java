package org.cubixmc.server.network;

import com.google.common.base.Charsets;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import org.cubixmc.inventory.ItemStack;
import org.cubixmc.inventory.Material;
import org.cubixmc.server.entity.Metadata;
import org.cubixmc.server.nbt.CompoundTag;
import org.cubixmc.server.nbt.ListTag;
import org.cubixmc.server.nbt.NBTStorage;
import org.cubixmc.server.nbt.NBTType;
import org.cubixmc.util.MathHelper;
import org.cubixmc.util.Position;

import java.util.UUID;

public class Codec {
    private final ByteBuf byteBuf;

    public Codec(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }

    public void writeByte(int value) {
        byteBuf.writeByte(value);
    }

    public void writeBoolean(boolean value) {
        byteBuf.writeBoolean(value);
    }

    public void writeInt(int value) {
        byteBuf.writeInt(value);
    }

    public void writeShort(int value) {
        byteBuf.writeShort(value);
    }

    public void writeFloat(float value) {
        byteBuf.writeFloat(value);
    }

    public void writeDouble(double value) {
        byteBuf.writeDouble(value);
    }

    public void writeLong(long value) {
        byteBuf.writeLong(value);
    }

    public void writeVarInt(int varInt) {
        writeVarInt(byteBuf, varInt);
    }

    public void writeVarLong(long varLong) {
        // TODO: Write var long
    }

    public void writeString(String utf) {
        writeVarInt(utf.length());
        byteBuf.writeBytes(utf.getBytes(Charsets.UTF_8));
    }

    public void writeChat(String chat) {
        if(chat.startsWith("{")) {
            // Already json
            writeString(chat);
        } else {
            JsonObject object = new JsonObject();
            object.addProperty("text", chat);
            writeString(object.toString());
        }
    }

    public void writeByteArray(byte[] bytes) {
        byteBuf.writeBytes(bytes);
    }

    public void writeBytes(byte[] bytes) {
        writeVarInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    public void writeBool(boolean bool) {
        byteBuf.writeByte(bool ? 1 : 0);
    }

    public void writeCompoundTag(CompoundTag compound) {
        if(compound == null) {
            writeByte(0);
        } else {
            NBTStorage.writeCompound(compound, new ByteBufOutputStream(byteBuf));
        }
    }

    public void writeSlot(ItemStack item) {
        if(item == null || item.getType() == Material.AIR) {
            writeShort(-1);
            return;
        }

        writeShort(item.getType().getId());
        writeByte(item.getAmount());
        writeShort(item.getData());
        // TODO: Support enchantments
        writeCompoundTag(null);
    }

    public void writePosition(Position position) {
        int x = MathHelper.floor(position.getX());
        int y = MathHelper.floor(position.getY());
        int z = MathHelper.floor(position.getZ());
        long value = ((x & 0x3FFFFFF) << 38) | ((y & 0xFFF) << 26) | (z & 0x3FFFFFF);
        writeLong(value);
    }

    public void writeUUID(UUID uuid) {
        writeLong(uuid.getMostSignificantBits());
        writeLong(uuid.getLeastSignificantBits());
    }

    public void writeMetadata(Metadata metadata) {
        metadata.encode(this);
    }

    public void writeVarInts(int[] integers) {
        writeVarInt(integers.length);
        for(int integer : integers) {
            writeVarInt(integer);
        }
    }

    public byte readByte() {
        return byteBuf.readByte();
    }

    public boolean readBoolean() {
        return byteBuf.readBoolean();
    }

    public int readInt() {
        return byteBuf.readInt();
    }

    public short readShort() {
        return byteBuf.readShort();
    }

    public float readFloat() {
        return byteBuf.readFloat();
    }

    public double readDouble() {
        return byteBuf.readDouble();
    }

    public long readLong() {
        return byteBuf.readLong();
    }

    public int readVarInt() {
        return readVarInt(byteBuf, 32);
    }

    public long readVarLong() {
        // TODO: Read VarLong
        return 0;
    }

    public String readString() {
        int length = readVarInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        return new String(bytes, Charsets.UTF_8);
    }

    public String readChat() {
        return readString();
    }

    public byte[] readBytes() {
        int length = readVarInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        return bytes;
    }

    public boolean readBool() {
        return byteBuf.readByte() != 0;
    }

    public short readUnsignedByte() {
        return byteBuf.readUnsignedByte();
    }

    public int readUnsignedShort() {
        return byteBuf.readUnsignedShort();
    }

    public CompoundTag readCompoundTag() {
        int index = byteBuf.readerIndex();
        if(readByte() == 0) {
            return null;
        }
        byteBuf.readerIndex(index);
        return NBTStorage.readCompound(new ByteBufInputStream(byteBuf));
    }

    public ItemStack readSlot() {
        int id = readShort();
        if(id < 0) {
            // Return air
            return null;
        }

        Material type = Material.getMaterial(id);
        int amount = readByte();
        short data = readShort();
        ItemStack item = new ItemStack(type, amount, data);
        CompoundTag enchantments = readCompoundTag();
        // TODO: Apply enchantments
        return item;
    }

    public Position readPosition() {
        long value = readLong();
        double x = (double) (value >> 38);
        double y = (double) ((value >> 26) & 0xFFF);
        double z = (double) (value << 38 >> 38);
        return new Position(null, x, y, z);
    }

    public UUID readUUID() {
        return new UUID(readLong(), readLong());
    }

    public Metadata readMetadata() {
        throw new UnsupportedOperationException("Not made yet");
    }

    public int[] readVarInts() {
        int length = readVarInt();
        int[] integers = new int[length];
        for(int i = 0; i < length; i++) {
            integers[i] = readVarInt();
        }

        return integers;
    }

    public static int readVarInt(ByteBuf byteBuf, int max) {
        int out = 0;
        int bytes = 0;
        byte in;
        while (true) {
            in = byteBuf.readByte();

            out |= (in & 0x7F) << (bytes++ * 7);

            if (bytes > max) {
                throw new RuntimeException("VarInt too big");
            }

            if ((in & 0x80) != 0x80) {
                break;
            }
        }

        return out;
    }

    public static void writeVarInt(ByteBuf byteBuf, int value) {
        int part;
        while (true) {
            part = value & 0x7F;

            value >>>= 7;
            if (value != 0) {
                part |= 0x80;
            }

            byteBuf.writeByte(part);

            if (value == 0) {
                break;
            }
        }
    }
}
