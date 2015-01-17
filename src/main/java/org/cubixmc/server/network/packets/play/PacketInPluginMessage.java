package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInPluginMessage extends PacketIn {
    private String channel;
    private byte[] data;

    public PacketInPluginMessage() {
        super(0x17);
    }

    @Override
    public void decode(Codec codec) {
        this.channel = codec.readString();
        this.data = codec.readBytes();
    }

    @Override
    public void handle(Connection connection) {
    }
}
