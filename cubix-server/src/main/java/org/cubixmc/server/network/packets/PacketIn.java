package org.cubixmc.server.network.packets;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;

public abstract class PacketIn extends Packet {

    public PacketIn(int id) {
        super(id);
    }

    public abstract void decode(Codec codec);

    public abstract void handle(Connection connection);

    public boolean isAsync() {
        return false;
    }
}
