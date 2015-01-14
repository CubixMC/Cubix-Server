package org.cubixmc.server.network;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;

public class Codec {
    private final ByteBuf byteBuf;

    public Codec(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
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
        // TODO: Write var int
    }

    public void writeVarLong(long varLong) {
        // TODO: Write var long
    }

    public void writeString(String utf) {
        writeVarInt(utf.length());
        byteBuf.writeBytes(utf.getBytes(Charsets.UTF_8));
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
        // TODO: Read VarInt
        return 0;
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
}
