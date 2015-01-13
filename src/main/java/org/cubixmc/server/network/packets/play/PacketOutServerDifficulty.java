package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutServerDifficulty extends PacketOut {
    private int difficulty;

    public PacketOutServerDifficulty(int difficulty) {
        super(0x41);
        this.difficulty = difficulty;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeByte(difficulty);
    }
}
