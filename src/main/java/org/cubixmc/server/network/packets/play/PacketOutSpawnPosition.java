package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;
import org.cubixmc.util.Position;

@Data
public class PacketOutSpawnPosition extends PacketOut {
    private Position location;

    public PacketOutSpawnPosition() {
        super(0x05);
    }

    public PacketOutSpawnPosition(Position location) {
        super(0x05);
        this.location = location;
    }

    @Override
    public void encode(Codec codec) {
        codec.writePosition(location);
    }
}
