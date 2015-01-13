package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

public class PacketInKeepAlive extends PacketIn {
    private int keepAliveID;

    public PacketInKeepAlive() {
        super(0x00);
    }

    @Override
    public void decode(Codec codec) {
        this.keepAliveID = codec.readVarInt();
    }

    public void handle() {
    }
}
