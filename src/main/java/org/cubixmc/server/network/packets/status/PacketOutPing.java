package org.cubixmc.server.network.packets.status;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutPing extends PacketOut {
    private long time;

    public PacketOutPing(long time) {
        super(0x01);
        this.time = time;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeLong(time);
    }
}
