package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutJoinGame extends PacketOut {
    private int difficulty;
    private int maxPlayers;
    private String levelType;
    private boolean reducedDebugInfo;
    private int entityID;
    private int gamemode;
    private int dimension;

    public PacketOutJoinGame() {
        super(0x01);
    }

    public PacketOutJoinGame(int difficultyint maxPlayersString levelTypeboolean reducedDebugInfoint entityIDint gamemodeint dimension) {
        super(0x01);
        this.difficulty = difficulty;
        this.maxPlayers = maxPlayers;
        this.levelType = levelType;
        this.reducedDebugInfo = reducedDebugInfo;
        this.entityID = entityID;
        this.gamemode = gamemode;
        this.dimension = dimension;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeByte(difficulty);
        codec.writeByte(maxPlayers);
        codec.writeString(levelType);
        codec.writeBoolean(reducedDebugInfo);
        codec.writeInt(entityID);
        codec.writeByte(gamemode);
        codec.writeByte(dimension);
    }
}
