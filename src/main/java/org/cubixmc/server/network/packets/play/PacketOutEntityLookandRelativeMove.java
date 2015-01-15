package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutEntityLookandRelativeMove extends PacketOut {
    private int dX;
    private boolean onGround;
    private int dY;
    private int dZ;
    private int entityID;
    private int pitch;
    private int yaw;

    public PacketOutEntityLookandRelativeMove() {
        super(0x17);
    }

    public PacketOutEntityLookandRelativeMove(int dX, boolean onGround, int dY, int dZ, int entityID, int pitch, int yaw) {
        super(0x17);
        this.dX = dX;
        this.onGround = onGround;
        this.dY = dY;
        this.dZ = dZ;
        this.entityID = entityID;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeByte(dX);
        codec.writeBoolean(onGround);
        codec.writeByte(dY);
        codec.writeByte(dZ);
        codec.writeVarInt(entityID);
        codec.writeByte(pitch);
        codec.writeByte(yaw);
    }
}
