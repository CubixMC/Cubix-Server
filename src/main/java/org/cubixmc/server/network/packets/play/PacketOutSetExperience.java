package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutSetExperience extends PacketOut {
    private int level;
    private int totalExperience;
    private float experienceBar;

    public PacketOutSetExperience(int levelint totalExperiencefloat experienceBar) {
        super(0x1F);
        this.level = level;
        this.totalExperience = totalExperience;
        this.experienceBar = experienceBar;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(level);
        codec.writeVarInt(totalExperience);
        codec.writeFloat(experienceBar);
    }
}
