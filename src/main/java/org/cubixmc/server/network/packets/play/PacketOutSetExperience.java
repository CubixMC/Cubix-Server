package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutSetExperience extends PacketOut {
    private int level;
    private int totalExperience;
    private float experienceBar;

    public PacketOutSetExperience() {
        super(0x1F);
    }

    public PacketOutSetExperience(int level, int totalExperience, float experienceBar) {
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
