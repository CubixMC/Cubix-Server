package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInResourcePackStatus extends PacketIn {
    private String hash;
    private int result;

    public PacketInResourcePackStatus() {
        super(0x19);
    }

    @Override
    public void decode(Codec codec) {
        this.hash = codec.readString();
        this.result = codec.readVarInt();
    }

    @Override
    public void handle(Connection connection) {
    }
}
