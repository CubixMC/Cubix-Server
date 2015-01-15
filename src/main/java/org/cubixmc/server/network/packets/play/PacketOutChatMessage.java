package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import lombok.Data;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutChatMessage extends PacketOut {
private int position;
private String jSONData;

public PacketOutChatMessage() {
super(0x02);
}

public PacketOutChatMessage(int position, String jSONData) {
super(0x02);
this.position = position;
this.jSONData = jSONData;
}

@Override
public void encode(Codec codec) {
codec.writeByte(position);
codec.writeString(jSONData);
}
}
