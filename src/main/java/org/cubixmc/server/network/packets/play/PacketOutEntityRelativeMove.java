package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.entity.CubixEntity;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;
import org.cubixmc.util.Vector3I;

@Data
public class PacketOutEntityRelativeMove extends PacketOut {
    private int entityID;
    private int dX;
    private int dY;
    private int dZ;
    private boolean onGround;

    public PacketOutEntityRelativeMove() {
        super(0x15);
    }

    public PacketOutEntityRelativeMove(CubixEntity entity, Vector3I movement) {
        this(entity.getEntityId(), movement.getX(), movement.getY(), movement.getZ(), true);
    }

    public PacketOutEntityRelativeMove(int entityID, int dX, int dY, int dZ, boolean onGround) {
        super(0x15);
        this.entityID = entityID;
        this.dX = dX;
        this.dY = dY;
        this.dZ = dZ;
        this.onGround = onGround;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(entityID);
        codec.writeByte(dX);
        codec.writeByte(dY);
        codec.writeByte(dZ);
        codec.writeBoolean(onGround);
    }
}
