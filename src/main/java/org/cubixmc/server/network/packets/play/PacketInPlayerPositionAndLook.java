package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

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
        this.x = codec.readFloat();
        this.feetY = codec.readFloat();
        this.z = codec.readFloat();
        this.yaw = codec.readFloat();
        this.pitch = codec.readFloat();
        this.onGround = codec.readBoolean();
    }

    @Override
    public void handle() {
    }
}
