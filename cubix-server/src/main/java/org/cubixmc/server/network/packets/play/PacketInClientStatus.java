package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInClientStatus extends PacketIn {
    private int actionID;

    public PacketInClientStatus() {
        super(0x16);
    }

    @Override
    public void decode(Codec codec) {
        this.actionID = codec.readVarInt();
    }

    @Override
    public void handle(Connection connection) {
    }
}
