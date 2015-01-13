package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInPluginMessage extends PacketIn {
    private byte[] data;
    private String channel;

    public PacketInPluginMessage() {
        super(0x17);
    }

    @Override
    public void decode(Codec codec) {
        this.data = codec.// TODO: Read bytes;
                this.channel = codec.readString();
    }

    @Override
    public void handle() {
    }
}
