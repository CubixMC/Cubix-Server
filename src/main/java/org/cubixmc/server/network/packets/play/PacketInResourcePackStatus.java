package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInResourcePackStatus extends PacketIn {
    private int result;
    private String hash;

    public PacketInResourcePackStatus() {
        super(0x19);
    }

    @Override
    public void decode(Codec codec) {
        this.result = codec.readVarInt();
        this.hash = codec.readString();
    }

    @Override
    public void handle() {
    }
}
