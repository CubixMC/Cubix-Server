package org.cubixmc.server.network.packets.login;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

public class PacketInLoginStart extends PacketIn {
    private String name;

    public PacketInLoginStart() {
        super(0x00);
    }

    @Override
    public void decode(Codec codec) {
        this.name = codec.readString();
    }

    public void handle() {
    }
}
