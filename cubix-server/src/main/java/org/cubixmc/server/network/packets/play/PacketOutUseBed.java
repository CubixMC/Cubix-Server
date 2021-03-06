package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.bukkit.Location;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutUseBed extends PacketOut {
    private int entityID;
    private Location location;

    public PacketOutUseBed() {
        super(0x0A);
    }

    public PacketOutUseBed(int entityID, Location location) {
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
