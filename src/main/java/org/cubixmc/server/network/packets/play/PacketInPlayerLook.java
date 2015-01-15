package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
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

    @Override
    public void handle() {
    }
}
