package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;
import org.cubixmc.util.Position;

@Getter
public class PacketInUpdateSign extends PacketIn {
    private Position location;
    private String line1;
    private String line2;
    private String line3;
    private String line4;

    public PacketInUpdateSign() {
        super(0x12);
    }

    @Override
    public void decode(Codec codec) {
        this.location = codec.readPosition();
        this.line1 = codec.readChat();
        this.line2 = codec.readChat();
        this.line3 = codec.readChat();
        this.line4 = codec.readChat();
    }

    @Override
    public void handle() {
    }
}
