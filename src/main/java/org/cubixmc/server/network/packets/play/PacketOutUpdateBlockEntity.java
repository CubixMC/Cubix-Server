package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import lombok.Data;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutUpdateBlockEntity extends PacketOut {
private byte[] nBTData;
private int action;
private Position location;

public PacketOutUpdateBlockEntity() {
super(0x35);
}

public PacketOutUpdateBlockEntity(byte[] nBTData, int action, Position location) {
super(0x35);
this.nBTData = nBTData;
this.action = action;
this.location = location;
}

@Override
public void encode(Codec codec) {
codec.writeBytes(nBTData);
codec.writeByte(action);
codec.writePosition(location);
}
}
