package org.cubixmc.server.network.packets;

import org.cubixmc.server.network.Codec;

public abstract class PacketIn extends Packet {

    public PacketIn(int id) {
        super(id);
    }

    public abstract void decode(Codec codec);

    public abstract void handle();
}
