package org.cubixmc.server.network.packets.status;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutResponse extends PacketOut {
    private String jSONResponse;

    public PacketOutResponse() {
        super(0x00);
    }

    public PacketOutResponse(String jSONResponse) {
        super(0x00);
        this.jSONResponse = jSONResponse;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeString(jSONResponse);
    }
}
