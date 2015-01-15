package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInUpdateSign extends PacketIn {
    private String line4;
    private Position location;
    private String line3;
    private String line2;
    private String line1;

    public PacketInUpdateSign() {
        super(0x12);
    }

    @Override
    public void decode(Codec codec) {
        this.line4 = codec.readChat();
        this.location = codec.readPosition();
        this.line3 = codec.readChat();
        this.line2 = codec.readChat();
        this.line1 = codec.readChat();
    }

    @Override
    public void handle() {
    }
}
