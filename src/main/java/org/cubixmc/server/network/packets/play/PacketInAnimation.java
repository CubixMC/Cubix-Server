package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

public class PacketInAnimation extends PacketIn {
    private int actionID;
    private int entityID;
    private int jumpBoost;

    public PacketInAnimation() {
        super(0x0B);
    }

    @Override
    public void decode(Codec codec) {
        this.actionID = codec.readVarInt();
        this.entityID = codec.readVarInt();
        this.jumpBoost = codec.readVarInt();
    }

    public void handle() {
    }
}
