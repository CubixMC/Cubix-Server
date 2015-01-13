package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

public class PacketInPlayerDigging extends PacketIn {
    private int face;
    private Position location;
    private int status;

    public PacketInPlayerDigging() {
        super(0x07);
    }

    @Override
    public void decode(Codec codec) {
        this.face = codec.readByte();
        this.location = codec.readPosition();
        this.status = codec.readByte();
    }

    public void handle() {
    }
}
