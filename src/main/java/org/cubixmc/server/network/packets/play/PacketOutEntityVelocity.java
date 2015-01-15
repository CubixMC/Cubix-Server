package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import lombok.Data;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutEntityVelocity extends PacketOut {
private int entityID;
private short velocityX;
private short velocityZ;
private short velocityY;

public PacketOutEntityVelocity() {
super(0x12);
}

public PacketOutEntityVelocity(int entityID, short velocityX, short velocityZ, short velocityY) {
super(0x12);
this.entityID = entityID;
this.velocityX = velocityX;
this.velocityZ = velocityZ;
this.velocityY = velocityY;
}

@Override
public void encode(Codec codec) {
codec.writeVarInt(entityID);
codec.writeShort(velocityX);
codec.writeShort(velocityZ);
codec.writeShort(velocityY);
}
}
