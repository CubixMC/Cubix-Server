package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutBlockChange extends PacketOut {
    private int blockID;
    private Position location;

    public PacketOutBlockChange() {
        super(0x23);
    }

    public PacketOutBlockChange(int blockIDPosition location) {
        super(0x23);
        this.blockID = blockID;
        this.location = location;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(blockID);
        codec.writePosition(location);
    }
}