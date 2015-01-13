package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutEntity extends PacketOut {
    private int entityID;

    public PacketOutEntity(int entityID) {
        super(0x14);
        this.entityID = entityID;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(entityID);
    }
}
