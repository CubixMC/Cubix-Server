package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutRemoveEntityEffect extends PacketOut {
    private int effectID;
    private int entityID;

    public PacketOutRemoveEntityEffect(int effectIDint entityID) {
        super(0x1E);
        this.effectID = effectID;
        this.entityID = entityID;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeByte(effectID);
        codec.writeVarInt(entityID);
    }
}
