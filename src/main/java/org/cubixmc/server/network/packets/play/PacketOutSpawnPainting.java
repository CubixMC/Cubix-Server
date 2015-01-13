package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutSpawnPainting extends PacketOut {
    private int entityID;
    private Position location;
    private String title;
    private int direction;

    public PacketOutSpawnPainting(int entityIDPosition locationString titleint direction) {
        super(0x10);
        this.entityID = entityID;
        this.location = location;
        this.title = title;
        this.direction = direction;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(entityID);
        codec.writePosition(location);
        codec.writeString(title);
        codec.writeByte(direction);
    }
}
