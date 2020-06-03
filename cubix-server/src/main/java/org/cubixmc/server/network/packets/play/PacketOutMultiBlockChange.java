package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;
import org.cubixmc.util.Vector2I;

import java.util.List;

public class PacketOutMultiBlockChange extends PacketOut {
    private Vector2I chunkPos;
    private List<int[]> blocks;

    public PacketOutMultiBlockChange(Vector2I chunkPos, List<int[]> blocks) {
        super(0x22);
        this.chunkPos = chunkPos;
        this.blocks = blocks;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeInt(chunkPos.getX());
        codec.writeInt(chunkPos.getZ());
        codec.writeVarInt(blocks.size());
        for(int[] block : blocks) {
            codec.writeByte(block[0]); //XZ-pos
            codec.writeByte(block[1]); //Y-pos
            codec.writeVarInt(block[2]); //Block id and data
        }
    }
}
