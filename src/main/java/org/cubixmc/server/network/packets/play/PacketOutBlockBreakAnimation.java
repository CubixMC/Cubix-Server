package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutBlockBreakAnimation extends PacketOut {
    private int entityID;
    private Position location;
    private int destroyStage;

    public PacketOutBlockBreakAnimation() {
        super(0x25);
    }

    public PacketOutBlockBreakAnimation(int entityID, Position location, int destroyStage) {
        super(0x25);
        this.entityID = entityID;
        this.location = location;
        this.destroyStage = destroyStage;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(entityID);
        codec.writePosition(location);
        codec.writeByte(destroyStage);
    }
}
