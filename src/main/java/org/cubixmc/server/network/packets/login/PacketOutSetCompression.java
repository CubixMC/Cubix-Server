package org.cubixmc.server.network.packets.login;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutSetCompression extends PacketOut {
    private int threshold;

    public PacketOutSetCompression(int threshold) {
        super(0x03);
        this.threshold = threshold;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(threshold);
    }
}
