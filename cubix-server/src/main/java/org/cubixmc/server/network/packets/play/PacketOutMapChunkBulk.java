package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;
import org.cubixmc.server.util.QueuedChunk;

import java.util.List;

@Data
public class PacketOutMapChunkBulk extends PacketOut {
    private boolean hasSKyLight;
    private List<QueuedChunk> chunks;

    public PacketOutMapChunkBulk(boolean hasSKyLight, List<QueuedChunk> chunks) {
        this();
        this.hasSKyLight = hasSKyLight;
        this.chunks = chunks;
    }

    public PacketOutMapChunkBulk() {
        super(0x26);
    }

    @Override
    public void encode(Codec codec) {
        chunks.forEach(QueuedChunk::build);
        codec.writeBool(hasSKyLight);
        codec.writeVarInt(chunks.size());
        for(QueuedChunk chunk : chunks) {
            codec.writeInt(chunk.getX());
            codec.writeInt(chunk.getZ());
            codec.writeShort(chunk.getSections() & 0xFFFF);
        }
        for(QueuedChunk chunk : chunks) {
            codec.writeByteArray(chunk.getBuffer());
        }

        chunks.clear();
    }
}
