package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInAnimation extends PacketIn {
    private int entityID;
    private int actionID;
    private int jumpBoost;

    public PacketInAnimation() {
        super(0x0B);
    }

    @Override
    public void decode(Codec codec) {
        this.entityID = codec.readVarInt();
        this.actionID = codec.readVarInt();
        this.jumpBoost = codec.readVarInt();
    }

    @Override
    public void handle() {
    }
}
