package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutCombatEvent extends PacketOut {
    private int duration;
    private int entityID;
    private int event;
    private String message;
    private int playerID;

    public PacketOutCombatEvent(int durationint entityIDint eventString messageint playerID) {
        super(0x42);
        this.duration = duration;
        this.entityID = entityID;
        this.event = event;
        this.message = message;
        this.playerID = playerID;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(duration);
        codec.writeInt(entityID);
        codec.writeVarInt(event);
        codec.writeString(message);
        codec.writeVarInt(playerID);
    }
}
