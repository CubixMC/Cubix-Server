package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutSetExperience extends PacketOut {
    private float experienceBar;
    private int level;
    private int totalExperience;

    public PacketOutSetExperience() {
        super(0x1F);
    }

    public PacketOutSetExperience(float experienceBar, int level, int totalExperience) {
        super(0x1F);
        this.experienceBar = experienceBar;
        this.level = level;
        this.totalExperience = totalExperience;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeFloat(experienceBar);
        codec.writeVarInt(level);
        codec.writeVarInt(totalExperience);
    }
}
