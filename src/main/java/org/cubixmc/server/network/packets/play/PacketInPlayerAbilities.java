package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
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

    @Override
    public void handle() {
    }
}
