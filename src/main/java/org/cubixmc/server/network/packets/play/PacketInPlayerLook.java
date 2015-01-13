package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

public class PacketInPlayerLook extends PacketIn {
    private boolean onGround;
    private float pitch;
    private float yaw;

    public PacketInPlayerLook() {
        super(0x05);
    }

    @Override
    public void decode(Codec codec) {
        this.onGround = codec.readBoolean();
        this.pitch = codec.readFloat();
        this.yaw = codec.readFloat();
    }

    public void handle() {
    }
}
