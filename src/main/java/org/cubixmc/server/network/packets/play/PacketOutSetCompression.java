package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutSetCompression extends PacketOut {
    private int threshold;

    public PacketOutSetCompression(int threshold) {
        super(0x46);
        this.threshold = threshold;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(threshold);
    }
}
