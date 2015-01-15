package org.cubixmc.server.network.packets.login;

import org.cubixmc.server.network.Codec;
import lombok.Data;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutDisconnect extends PacketOut {
private String jSONData;

public PacketOutDisconnect() {
super(0x00);
}

public PacketOutDisconnect(String jSONData) {
super(0x00);
this.jSONData = jSONData;
}

@Override
public void encode(Codec codec) {
codec.writeString(jSONData);
}
}
