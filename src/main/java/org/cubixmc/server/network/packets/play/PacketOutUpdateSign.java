package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import lombok.Data;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutUpdateSign extends PacketOut {
private String line4;
private Position location;
private String line3;
private String line2;
private String line1;

public PacketOutUpdateSign() {
super(0x33);
}

public PacketOutUpdateSign(String line4, Position location, String line3, String line2, String line1) {
super(0x33);
this.line4 = line4;
this.location = location;
this.line3 = line3;
this.line2 = line2;
this.line1 = line1;
}

@Override
public void encode(Codec codec) {
codec.writeChat(line4);
codec.writePosition(location);
codec.writeChat(line3);
codec.writeChat(line2);
codec.writeChat(line1);
}
}
