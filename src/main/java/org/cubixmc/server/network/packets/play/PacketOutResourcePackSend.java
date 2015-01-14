package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutResourcePackSend extends PacketOut {
    private String uRL;
    private String hash;

    public PacketOutResourcePackSend() {
        super(0x48);
    }

    public PacketOutResourcePackSend(String uRLString hash) {
        super(0x48);
        this.uRL = uRL;
        this.hash = hash;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeString(uRL);
        codec.writeString(hash);
    }
}
