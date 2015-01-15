package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutChunkData extends PacketOut {
    private int chunkX;
    private int chunkZ;
    private boolean groundUpContinuous;
    private short primaryBitMap;
    private int size;
    private byte[] data;

    public PacketOutChunkData() {
        super(0x21);
    }

    public PacketOutChunkData(int chunkX, int chunkZ, boolean groundUpContinuous, short primaryBitMap, int size, byte[] data) {
        super(0x21);
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.groundUpContinuous = groundUpContinuous;
        this.primaryBitMap = primaryBitMap;
        this.size = size;
        this.data = data;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeInt(chunkX);
        codec.writeInt(chunkZ);
        codec.writeBoolean(groundUpContinuous);
        codec.writeShort(primaryBitMap);
        codec.writeVarInt(size);
        codec.writeBytes(data);
    }
}
