package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutDisconnect extends PacketOut {
    private String reason;

    public PacketOutDisconnect() {
        super(0x40);
    }

    public PacketOutDisconnect(String reason) {
        super(0x40);
        this.reason = reason;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeString(reason);
    }
}
