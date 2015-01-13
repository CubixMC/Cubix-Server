package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

public class PacketInPlayerAbilities extends PacketIn {
    private int flags;
    private float flyingSpeed;
    private float walkingSpeed;

    public PacketInPlayerAbilities() {
        super(0x13);
    }

    @Override
    public void decode(Codec codec) {
        this.flags = codec.readByte();
        this.flyingSpeed = codec.readFloat();
        this.walkingSpeed = codec.readFloat();
    }

    public void handle() {
    }
}
