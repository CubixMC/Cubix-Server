package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;
import org.cubixmc.util.Position;

@Data
public class PacketOutUseBed extends PacketOut {
    private int entityID;
    private Position location;

    public PacketOutUseBed() {
        super(0x0A);
    }

    public PacketOutUseBed(int entityID, Position location) {
        super(0x0A);
        this.entityID = entityID;
        this.location = location;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(entityID);
        codec.writePosition(location);
    }
}
