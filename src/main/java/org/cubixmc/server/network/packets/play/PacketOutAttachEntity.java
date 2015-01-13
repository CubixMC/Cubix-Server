package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutAttachEntity extends PacketOut {
    private int entityID;
    private int vehicleID;
    private boolean leash;

    public PacketOutAttachEntity(int entityIDint vehicleIDboolean leash) {
        super(0x1B);
        this.entityID = entityID;
        this.vehicleID = vehicleID;
        this.leash = leash;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeInt(entityID);
        codec.writeInt(vehicleID);
        codec.writeBoolean(leash);
    }
}
