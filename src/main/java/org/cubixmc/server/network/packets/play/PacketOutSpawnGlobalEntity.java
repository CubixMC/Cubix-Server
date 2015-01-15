package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import lombok.Data;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutSpawnGlobalEntity extends PacketOut {
private int x;
private int y;
private int entityID;
private int z;
private int type;

public PacketOutSpawnGlobalEntity() {
super(0x2C);
}

public PacketOutSpawnGlobalEntity(int x, int y, int entityID, int z, int type) {
super(0x2C);
this.x = x;
this.y = y;
this.entityID = entityID;
this.z = z;
this.type = type;
}

@Override
public void encode(Codec codec) {
codec.writeInt(x);
codec.writeInt(y);
codec.writeVarInt(entityID);
codec.writeInt(z);
codec.writeByte(type);
}
}
