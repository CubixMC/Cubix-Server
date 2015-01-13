package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutEntityEffect extends PacketOut {
    private int duration;
    private boolean hideParticles;
    private int effectID;
    private int entityID;
    private int amplifier;

    public PacketOutEntityEffect(int durationboolean hideParticlesint effectIDint entityIDint amplifier) {
        super(0x1D);
        this.duration = duration;
        this.hideParticles = hideParticles;
        this.effectID = effectID;
        this.entityID = entityID;
        this.amplifier = amplifier;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(duration);
        codec.writeBoolean(hideParticles);
        codec.writeByte(effectID);
        codec.writeVarInt(entityID);
        codec.writeByte(amplifier);
    }
}
