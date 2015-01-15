package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import lombok.Data;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutEntity extends PacketOut {
private int entityID;

public PacketOutEntity() {
super(0x14);
}

public PacketOutEntity(int entityID) {
super(0x14);
this.entityID = entityID;
}

@Override
public void encode(Codec codec) {
codec.writeVarInt(entityID);
}
}
