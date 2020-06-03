package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutPluginMessage extends PacketOut {
    private String channel;
    private byte[] data;

    public PacketOutPluginMessage() {
        super(0x3F);
    }

    public PacketOutPluginMessage(String channel, byte[] data) {
        super(0x3F);
        this.channel = channel;
        this.data = data;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeString(channel);
        codec.writeBytes(data);
    }
}
