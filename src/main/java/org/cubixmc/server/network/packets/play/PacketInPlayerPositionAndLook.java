package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketIn;
import org.cubixmc.util.MathHelper;
import org.cubixmc.util.Position;

@Getter
public class PacketInPlayerPositionAndLook extends PacketIn {
    private double x;
    private double feetY;
    private double z;
    private float yaw;
    private float pitch;
    private boolean onGround;

    public PacketInPlayerPositionAndLook() {
        super(0x06);
    }

    @Override
    public void decode(Codec codec) {
        this.x = codec.readDouble();
        this.feetY = codec.readDouble();
        this.z = codec.readDouble();
        this.yaw = codec.readFloat();
        this.pitch = codec.readFloat();
        this.onGround = codec.readBool();
    }

    @Override
    public void handle(Connection connection) {
        Position position = connection.getPlayer().getPosition();
        int dx = (MathHelper.floor(x) >> 4) - (MathHelper.floor(position.getX()) >> 4);
        int dz = (MathHelper.floor(z) >> 4) - (MathHelper.floor(position.getZ()) >> 4);
        position.setX(x);
        position.setY(feetY);
        position.setZ(z);
        position.setYaw(yaw);
        position.setPitch(pitch);
        if(dx != 0 || dz != 0) {
            connection.getPlayer().getPlayerChunkMap().movePlayer(dx, dz);
        }
    }
}
