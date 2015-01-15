package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutJoinGame extends PacketOut {
    private int entityID;
    private int gamemode;
    private int dimension;
    private int difficulty;
    private int maxPlayers;
    private String levelType;
    private boolean reducedDebugInfo;

    public PacketOutJoinGame() {
        super(0x01);
    }

    public PacketOutJoinGame(int entityID, int gamemode, int dimension, int difficulty, int maxPlayers, String levelType, boolean reducedDebugInfo) {
        super(0x01);
        this.entityID = entityID;
        this.gamemode = gamemode;
        this.dimension = dimension;
        this.difficulty = difficulty;
        this.maxPlayers = maxPlayers;
        this.levelType = levelType;
        this.reducedDebugInfo = reducedDebugInfo;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeInt(entityID);
        codec.writeByte(gamemode);
        codec.writeByte(dimension);
        codec.writeByte(difficulty);
        codec.writeByte(maxPlayers);
        codec.writeString(levelType);
        codec.writeBoolean(reducedDebugInfo);
    }
}
