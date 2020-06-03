package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutCombatEvent extends PacketOut {
    private int event;
    private int duration;
    private int entityID;
    private int playerID;
    private int entityID0;
    private String message;

    public PacketOutCombatEvent() {
        super(0x42);
    }

    public PacketOutCombatEvent(int event, int duration, int entityID, int playerID, int entityID0, String message) {
        super(0x42);
        this.event = event;
        this.duration = duration;
        this.entityID = entityID;
        this.playerID = playerID;
        this.entityID0 = entityID0;
        this.message = message;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(event);
        codec.writeVarInt(duration);
        codec.writeInt(entityID);
        codec.writeVarInt(playerID);
        codec.writeInt(entityID0);
        codec.writeString(message);
    }
}
