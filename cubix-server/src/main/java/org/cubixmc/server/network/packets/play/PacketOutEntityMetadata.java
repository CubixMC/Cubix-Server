package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.entity.Metadata;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutEntityMetadata extends PacketOut {
    private int entityID;
    private Metadata metadata;

    public PacketOutEntityMetadata() {
        super(0x1C);
    }

    public PacketOutEntityMetadata(int entityID, Metadata metadata) {
        super(0x1C);
        this.entityID = entityID;
        this.metadata = metadata;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(entityID);
        codec.writeMetadata(metadata);
    }
}
