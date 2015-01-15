package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutKeepAlive extends PacketOut {
    private int keepAliveID;

    public PacketOutKeepAlive() {
        super(0x00);
    }

    public PacketOutKeepAlive(int keepAliveID) {
        super(0x00);
        this.keepAliveID = keepAliveID;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(keepAliveID);
    }
}
