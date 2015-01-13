package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutHeldItemChange extends PacketOut {
    private int slot;

    public PacketOutHeldItemChange(int slot) {
        super(0x09);
        this.slot = slot;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeByte(slot);
    }
}
