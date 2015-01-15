package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import lombok.Data;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutEntityLook extends PacketOut {
private boolean onGround;
private int entityID;
private int pitch;
private int yaw;

public PacketOutEntityLook() {
super(0x16);
}

public PacketOutEntityLook(boolean onGround, int entityID, int pitch, int yaw) {
super(0x16);
this.onGround = onGround;
this.entityID = entityID;
this.pitch = pitch;
this.yaw = yaw;
}

@Override
public void encode(Codec codec) {
codec.writeBoolean(onGround);
codec.writeVarInt(entityID);
codec.writeByte(pitch);
codec.writeByte(yaw);
}
}
