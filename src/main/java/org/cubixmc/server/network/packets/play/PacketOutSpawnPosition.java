package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutSpawnPosition extends PacketOut {
    private Position location;

    public PacketOutSpawnPosition(Position location) {
        super(0x05);
        this.location = location;
    }

    @Override
    public void encode(Codec codec) {
        codec.writePosition(location);
    }
}
