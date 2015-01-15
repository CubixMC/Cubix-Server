package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

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
        this.x = codec.readFloat();
        this.feetY = codec.readFloat();
        this.z = codec.readFloat();
        this.onGround = codec.readBoolean();
    }

    @Override
    public void handle() {
    }
}
