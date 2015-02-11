package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketIn;
import org.cubixmc.util.MathHelper;
import org.cubixmc.util.Position;

@Getter
public class PacketInPlayerPosition extends PacketIn {
    private double x;
    private double feetY;
    private double z;
    private boolean onGround;

    public PacketInPlayerPosition() {
        super(0x04);
    }

    @Override
    public void decode(Codec codec) {
        this.x = codec.readDouble();
        this.feetY = codec.readDouble();
        this.z = codec.readDouble();
        this.onGround = codec.readBool();
    }

    @Override
    public void handle(Connection connection) {
        // Make the absolute position relative
        Position pos = connection.getPlayer().getPosition();
        double dx = x - pos.getX();
        double dy = feetY - pos.getY();
        double dz = z - pos.getZ();
        connection.getPlayer().move(dx, dy, dz);
    }
}
