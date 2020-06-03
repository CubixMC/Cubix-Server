package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;
import org.cubixmc.util.Position;

@Data
public class PacketOutSpawnPainting extends PacketOut {
    private int entityID;
    private String title;
    private Position location;
    private int direction;

    public PacketOutSpawnPainting() {
        super(0x10);
    }

    public PacketOutSpawnPainting(int entityID, String title, Position location, int direction) {
        super(0x10);
        this.entityID = entityID;
        this.title = title;
        this.location = location;
        this.direction = direction;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(entityID);
        codec.writeString(title);
        codec.writePosition(location);
        codec.writeByte(direction);
    }
}
