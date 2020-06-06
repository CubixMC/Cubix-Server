package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.bukkit.Location;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutSpawnPosition extends PacketOut {
    private Location location;

    public PacketOutSpawnPosition() {
        super(0x05);
    }

    public PacketOutSpawnPosition(Location location) {
        super(0x05);
        this.location = location;
    }

    @Override
    public void encode(Codec codec) {
        codec.writePosition(location);
    }
}
