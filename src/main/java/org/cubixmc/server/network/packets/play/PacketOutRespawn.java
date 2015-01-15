package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutRespawn extends PacketOut {
    private int dimension;
    private int difficulty;
    private int gamemode;
    private String levelType;

    public PacketOutRespawn() {
        super(0x07);
    }

    public PacketOutRespawn(int dimension, int difficulty, int gamemode, String levelType) {
        super(0x07);
        this.dimension = dimension;
        this.difficulty = difficulty;
        this.gamemode = gamemode;
        this.levelType = levelType;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeInt(dimension);
        codec.writeByte(difficulty);
        codec.writeByte(gamemode);
        codec.writeString(levelType);
    }
}
