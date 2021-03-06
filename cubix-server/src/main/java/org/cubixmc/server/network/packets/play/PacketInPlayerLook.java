package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInPlayerLook extends PacketIn {
    private float yaw;
    private float pitch;
    private boolean onGround;

    public PacketInPlayerLook() {
        super(0x05);
    }

    @Override
    public void decode(Codec codec) {
        this.yaw = codec.readFloat();
        this.pitch = codec.readFloat();
        this.onGround = codec.readBool();
    }

    @Override
    public void handle(Connection connection) {
        connection.getPlayer().setRotation(yaw, pitch);
    }
}
