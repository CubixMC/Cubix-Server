package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketIn;

import java.util.UUID;

@Getter
public class PacketInSpectate extends PacketIn {
    private UUID targetPlayer;

    public PacketInSpectate() {
        super(0x18);
    }

    @Override
    public void decode(Codec codec) {
        this.targetPlayer = codec.readUUID();
    }

    @Override
    public void handle(Connection connection) {
    }
}
