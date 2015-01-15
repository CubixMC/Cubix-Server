package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import lombok.Data;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutEntityEffect extends PacketOut {
private int duration;
private boolean hideParticles;
private int effectID;
private int entityID;
private int amplifier;

public PacketOutEntityEffect() {
super(0x1D);
}

public PacketOutEntityEffect(int duration, boolean hideParticles, int effectID, int entityID, int amplifier) {
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
