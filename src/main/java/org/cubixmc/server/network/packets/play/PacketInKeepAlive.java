package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInKeepAlive extends PacketIn {
    private int keepAliveID;

    public PacketInKeepAlive() {
        super(0x00);
    }

    @Override
    public void decode(Codec codec) {
        this.keepAliveID = codec.readVarInt();
    }

    @Override
    public void handle() {
    }
}
