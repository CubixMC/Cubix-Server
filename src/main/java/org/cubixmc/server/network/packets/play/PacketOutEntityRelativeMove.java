package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutEntityRelativeMove extends PacketOut {
    private int dX;
    private boolean onGround;
    private int dY;
    private int dZ;
    private int entityID;

    public PacketOutEntityRelativeMove() {
        super(0x15);
    }

    public PacketOutEntityRelativeMove(int dX, boolean onGround, int dY, int dZ, int entityID) {
        super(0x15);
        this.dX = dX;
        this.onGround = onGround;
        this.dY = dY;
        this.dZ = dZ;
        this.entityID = entityID;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeByte(dX);
        codec.writeBoolean(onGround);
        codec.writeByte(dY);
        codec.writeByte(dZ);
        codec.writeVarInt(entityID);
    }
}
