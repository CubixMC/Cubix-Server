package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutEntityLookandRelativeMove extends PacketOut {
    private int entityID;
    private int dX;
    private int dY;
    private int dZ;
    private int yaw;
    private int pitch;
    private boolean onGround;

    public PacketOutEntityLookandRelativeMove() {
        super(0x17);
    }

    public PacketOutEntityLookandRelativeMove(int entityID, int dX, int dY, int dZ, int yaw, int pitch, boolean onGround) {
        super(0x17);
        this.entityID = entityID;
        this.dX = dX;
        this.dY = dY;
        this.dZ = dZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(entityID);
        codec.writeByte(dX);
        codec.writeByte(dY);
        codec.writeByte(dZ);
        codec.writeByte(yaw);
        codec.writeByte(pitch);
        codec.writeBoolean(onGround);
    }
}
