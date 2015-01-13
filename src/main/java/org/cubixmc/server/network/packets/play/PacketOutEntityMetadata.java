package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutEntityMetadata extends PacketOut {
    private Metadata metadata;
    private int entityID;

    public PacketOutEntityMetadata() {
        super(0x1C);
    }

    public PacketOutEntityMetadata(Metadata metadataint entityID) {
        super(0x1C);
        this.metadata = metadata;
        this.entityID = entityID;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeMetadata(metadata);
        codec.writeVarInt(entityID);
    }
}