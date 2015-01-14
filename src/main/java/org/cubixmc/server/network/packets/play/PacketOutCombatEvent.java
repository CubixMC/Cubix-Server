package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutCombatEvent extends PacketOut {
    private int duration;
    private int entityID0;
    private int entityID;
    private int event;
    private String message;
    private int playerID;

    public PacketOutCombatEvent() {
        super(0x42);
    }

    public PacketOutCombatEvent(int durationint entityID0int entityIDint eventString messageint playerID) {
        super(0x42);
        this.duration = duration;
        this.entityID0 = entityID0;
        this.entityID = entityID;
        this.event = event;
        this.message = message;
        this.playerID = playerID;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(duration);
        codec.writeInt(entityID0);
        codec.writeInt(entityID);
        codec.writeVarInt(event);
        codec.writeString(message);
        codec.writeVarInt(playerID);
    }
}
