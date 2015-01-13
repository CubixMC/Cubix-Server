package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutPlayerAbilities extends PacketOut {
    private int flags;
    private float flyingSpeed;
    private float walkingSpeed;

    public PacketOutPlayerAbilities() {
        super(0x39);
    }

    public PacketOutPlayerAbilities(int flagsfloat flyingSpeedfloat walkingSpeed) {
        super(0x39);
        this.flags = flags;
        this.flyingSpeed = flyingSpeed;
        this.walkingSpeed = walkingSpeed;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeByte(flags);
        codec.writeFloat(flyingSpeed);
        codec.writeFloat(walkingSpeed);
    }
}
