package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutBlockBreakAnimation extends PacketOut {
    private int entityID;
    private Position location;
    private int destroyStage;

    public PacketOutBlockBreakAnimation(int entityIDPosition locationint destroyStage) {
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
