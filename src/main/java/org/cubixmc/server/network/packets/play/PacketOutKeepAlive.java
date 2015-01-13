package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutKeepAlive extends PacketOut {
    private int keepAliveID;

    public PacketOutKeepAlive(int keepAliveID) {
        super(0x00);
        this.keepAliveID = keepAliveID;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(keepAliveID);
    }
}
