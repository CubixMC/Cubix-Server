package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutEntityEffect extends PacketOut {
    private int entityID;
    private int effectID;
    private int amplifier;
    private int duration;
    private boolean hideParticles;

    public PacketOutEntityEffect() {
        super(0x1D);
    }

    public PacketOutEntityEffect(int entityID, int effectID, int amplifier, int duration, boolean hideParticles) {
        super(0x1D);
        this.entityID = entityID;
        this.effectID = effectID;
        this.amplifier = amplifier;
        this.duration = duration;
        this.hideParticles = hideParticles;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(entityID);
        codec.writeByte(effectID);
        codec.writeByte(amplifier);
        codec.writeVarInt(duration);
        codec.writeBoolean(hideParticles);
    }
}
