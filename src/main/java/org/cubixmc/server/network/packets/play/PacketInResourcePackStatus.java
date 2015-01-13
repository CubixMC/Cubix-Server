package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

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

    public void handle() {
    }
}
