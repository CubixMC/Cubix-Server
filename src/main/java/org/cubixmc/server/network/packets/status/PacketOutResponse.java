package org.cubixmc.server.network.packets.status;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutResponse extends PacketOut {
    private String jSONResponse;

    public PacketOutResponse(String jSONResponse) {
        super(0x00);
        this.jSONResponse = jSONResponse;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeString(jSONResponse);
    }
}
