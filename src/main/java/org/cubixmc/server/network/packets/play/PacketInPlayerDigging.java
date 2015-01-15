package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;
import org.cubixmc.util.Position;

@Getter
public class PacketInPlayerDigging extends PacketIn {
    private int status;
    private Position location;
    private int face;

    public PacketInPlayerDigging() {
        super(0x07);
    }

    @Override
    public void decode(Codec codec) {
        this.status = codec.readByte();
        this.location = codec.readPosition();
        this.face = codec.readByte();
    }

    @Override
    public void handle() {
    }
}
