package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutChangeGameState extends PacketOut {
    private int reason;
    private float value;

    public PacketOutChangeGameState(int reasonfloat value) {
        super(0x2B);
        this.reason = reason;
        this.value = value;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeByte(reason);
        codec.writeFloat(value);
    }
}
