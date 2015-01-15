package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutChunkData extends PacketOut {
    private int size;
    private byte[] data;
    private short primaryBitMap;
    private int chunkX;
    private boolean groundUpContinuous;
    private int chunkZ;

    public PacketOutChunkData() {
        super(0x21);
    }

    public PacketOutChunkData(int size, byte[] data, short primaryBitMap, int chunkX, boolean groundUpContinuous, int chunkZ) {
        super(0x21);
        this.size = size;
        this.data = data;
        this.primaryBitMap = primaryBitMap;
        this.chunkX = chunkX;
        this.groundUpContinuous = groundUpContinuous;
        this.chunkZ = chunkZ;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(size);
        codec.writeBytes(data);
        codec.writeShort(primaryBitMap);
        codec.writeInt(chunkX);
        codec.writeBoolean(groundUpContinuous);
        codec.writeInt(chunkZ);
    }
}
