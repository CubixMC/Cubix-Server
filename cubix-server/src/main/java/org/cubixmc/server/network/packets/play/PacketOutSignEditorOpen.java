package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.bukkit.Location;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutSignEditorOpen extends PacketOut {
    private Location location;

    public PacketOutSignEditorOpen() {
        super(0x36);
    }

    public PacketOutSignEditorOpen(Location location) {
        super(0x36);
        this.location = location;
    }

    @Override
    public void encode(Codec codec) {
        codec.writePosition(location);
    }
}
