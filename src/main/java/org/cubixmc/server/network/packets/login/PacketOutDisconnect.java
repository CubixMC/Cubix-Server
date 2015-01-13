package org.cubixmc.server.network.packets.login;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutDisconnect extends PacketOut {
    private String jSONData;

    public PacketOutDisconnect(String jSONData) {
        super(0x00);
        this.jSONData = jSONData;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeString(jSONData);
    }
}
