package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;
import org.cubixmc.util.Position;

@Data
public class PacketOutBlockChange extends PacketOut {
    private Position location;
    private int blockID;

    public PacketOutBlockChange() {
        super(0x23);
    }

    public PacketOutBlockChange(Position location, int blockID) {
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
