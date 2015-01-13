package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutBlockChange extends PacketOut {
    private int blockID;
    private Position location;

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
