package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutEntityStatus extends PacketOut {
    private int entityStatus;
    private int entityID;

    public PacketOutEntityStatus(int entityStatusint entityID) {
        super(0x1A);
        this.entityStatus = entityStatus;
        this.entityID = entityID;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeByte(entityStatus);
        codec.writeInt(entityID);
    }
}
