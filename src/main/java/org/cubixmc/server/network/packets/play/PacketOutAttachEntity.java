package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutAttachEntity extends PacketOut {
    private int entityID;
    private int vehicleID;
    private boolean leash;

    public PacketOutAttachEntity() {
        super(0x1B);
    }

    public PacketOutAttachEntity(int entityID, int vehicleID, boolean leash) {
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
