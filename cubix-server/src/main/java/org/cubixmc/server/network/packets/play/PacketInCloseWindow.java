package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInCloseWindow extends PacketIn {
    private int windowId;

    public PacketInCloseWindow() {
        super(0x0D);
    }

    @Override
    public void decode(Codec codec) {
        this.windowId = codec.readByte();
    }

    @Override
    public void handle(Connection connection) {
    }
}
