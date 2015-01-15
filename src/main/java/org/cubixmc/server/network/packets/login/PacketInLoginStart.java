package org.cubixmc.server.network.packets.login;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInLoginStart extends PacketIn {
    private String name;

    public PacketInLoginStart() {
        super(0x00);
    }

    @Override
    public void decode(Codec codec) {
        this.name = codec.readString();
    }

    @Override
    public void handle() {
    }
}
