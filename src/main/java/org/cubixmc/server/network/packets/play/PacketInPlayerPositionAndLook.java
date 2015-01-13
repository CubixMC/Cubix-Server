package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

public class PacketInPlayerPositionAndLook extends PacketIn {
    private boolean onGround;
    private double feetY;
    private double x;
    private double z;
    private float pitch;
    private float yaw;

    public PacketInPlayerPositionAndLook() {
        super(0x06);
    }

    @Override
    public void decode(Codec codec) {
        this.onGround = codec.readBoolean();
        this.feetY = codec.readFloat();
        this.x = codec.readFloat();
        this.z = codec.readFloat();
        this.pitch = codec.readFloat();
        this.yaw = codec.readFloat();
    }

    public void handle() {
    }
}
