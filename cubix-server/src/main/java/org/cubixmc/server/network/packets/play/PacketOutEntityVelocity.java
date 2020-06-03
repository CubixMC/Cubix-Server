package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutEntityVelocity extends PacketOut {
    private int entityID;
    private short velocityX;
    private short velocityY;
    private short velocityZ;

    public PacketOutEntityVelocity() {
        super(0x12);
    }

    public PacketOutEntityVelocity(int entityID, short velocityX, short velocityY, short velocityZ) {
        super(0x12);
        this.entityID = entityID;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.velocityZ = velocityZ;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(entityID);
        codec.writeShort(velocityX);
        codec.writeShort(velocityY);
        codec.writeShort(velocityZ);
    }
}
