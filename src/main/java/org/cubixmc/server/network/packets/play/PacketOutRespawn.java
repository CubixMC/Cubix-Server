package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutRespawn extends PacketOut {
    private int difficulty;
    private String levelType;
    private int dimension;
    private int gamemode;

    public PacketOutRespawn(int difficultyString levelTypeint dimensionint gamemode) {
        super(0x07);
        this.difficulty = difficulty;
        this.levelType = levelType;
        this.dimension = dimension;
        this.gamemode = gamemode;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeByte(difficulty);
        codec.writeString(levelType);
        codec.writeInt(dimension);
        codec.writeByte(gamemode);
    }
}
