package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutEntityHeadLook extends PacketOut {
    private int headYaw;
    private int entityID;

    public PacketOutEntityHeadLook() {
        super(0x19);
    }

    public PacketOutEntityHeadLook(int headYawint entityID) {
        super(0x19);
        this.headYaw = headYaw;
        this.entityID = entityID;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeByte(headYaw);
        codec.writeVarInt(entityID);
    }
}
