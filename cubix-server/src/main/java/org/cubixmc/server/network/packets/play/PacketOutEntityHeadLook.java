package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutEntityHeadLook extends PacketOut {
    private int entityID;
    private int headYaw;

    public PacketOutEntityHeadLook() {
        super(0x19);
    }

    public PacketOutEntityHeadLook(int entityID, int headYaw) {
        super(0x19);
        this.entityID = entityID;
        this.headYaw = headYaw;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(entityID);
        codec.writeByte(headYaw);
    }
}
