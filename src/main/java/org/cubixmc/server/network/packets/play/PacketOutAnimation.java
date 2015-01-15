package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutAnimation extends PacketOut {
    private int entityID;
    private int animation;

    public PacketOutAnimation() {
        super(0x0B);
    }

    public PacketOutAnimation(int entityID, int animation) {
        super(0x0B);
        this.entityID = entityID;
        this.animation = animation;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(entityID);
        codec.writeByte(animation);
    }
}
