package org.cubixmc.server.network.packets;

import org.cubixmc.server.network.Codec;

public abstract class PacketOut extends Packet {

    public PacketOut(int id) {
        super(id);
    }

    public abstract void encode(Codec codec);
}
