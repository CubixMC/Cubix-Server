package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import lombok.Data;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutChunkData extends PacketOut {
private int size;
private byte[] data;
private short primaryBitMap;
private int chunkX;
private int chunkZ;
private boolean ground-UpContinuous;

public PacketOutChunkData() {
super(0x21);
}

public PacketOutChunkData(int size, byte[] data, short primaryBitMap, int chunkX, int chunkZ, boolean ground-UpContinuous) {
super(0x21);
this.size = size;
this.data = data;
this.primaryBitMap = primaryBitMap;
this.chunkX = chunkX;
this.chunkZ = chunkZ;
this.ground-UpContinuous = ground-UpContinuous;
}

@Override
public void encode(Codec codec) {
codec.writeVarInt(size);
codec.writeBytes(data);
codec.writeShort(primaryBitMap);
codec.writeInt(chunkX);
codec.writeInt(chunkZ);
codec.writeBoolean(ground-UpContinuous);
}
}
