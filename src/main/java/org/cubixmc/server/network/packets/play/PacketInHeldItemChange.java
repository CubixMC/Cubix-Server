package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

public class PacketInHeldItemChange extends PacketIn {
    private short slot;

    public PacketInHeldItemChange() {
        super(0x09);
    }

    @Override
    public void decode(Codec codec) {
        this.slot = codec.readShort();
    }

    public void handle() {
    }
}
