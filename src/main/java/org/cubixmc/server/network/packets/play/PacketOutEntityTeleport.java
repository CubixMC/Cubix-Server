package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import lombok.Data;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutEntityTeleport extends PacketOut {
private boolean onGround;
private int x;
private int y;
private int entityID;
private int z;
private int pitch;
private int yaw;

public PacketOutEntityTeleport() {
super(0x18);
}

public PacketOutEntityTeleport(boolean onGround, int x, int y, int entityID, int z, int pitch, int yaw) {
super(0x18);
this.onGround = onGround;
this.x = x;
this.y = y;
this.entityID = entityID;
this.z = z;
this.pitch = pitch;
this.yaw = yaw;
}

@Override
public void encode(Codec codec) {
codec.writeBoolean(onGround);
codec.writeInt(x);
codec.writeInt(y);
codec.writeVarInt(entityID);
codec.writeInt(z);
codec.writeByte(pitch);
codec.writeByte(yaw);
}
}
