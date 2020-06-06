package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.bukkit.Location;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutBlockChange extends PacketOut {
    private Location location;
    private int blockID;

    public PacketOutBlockChange() {
        super(0x23);
    }

    public PacketOutBlockChange(Location location, int blockID) {
        super(0x23);
        this.location = location;
        this.blockID = blockID;
    }

    @Override
    public void encode(Codec codec) {
        codec.writePosition(location);
        codec.writeVarInt(blockID);
    }
}
