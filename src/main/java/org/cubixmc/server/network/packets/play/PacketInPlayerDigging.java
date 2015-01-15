package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
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

    @Override
    public void handle() {
    }
}
