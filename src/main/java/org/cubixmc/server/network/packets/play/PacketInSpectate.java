package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

public class PacketInSpectate extends PacketIn {
    private UUID targetPlayer;

    public PacketInSpectate() {
        super(0x18);
    }

    @Override
    public void decode(Codec codec) {
        this.targetPlayer = codec.readUUID();
    }

    public void handle() {
    }
}
