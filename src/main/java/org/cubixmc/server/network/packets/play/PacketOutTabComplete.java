package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import lombok.Data;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutTabComplete extends PacketOut {
private int count;
private String match;

public PacketOutTabComplete() {
super(0x3A);
}

public PacketOutTabComplete(int count, String match) {
super(0x3A);
this.count = count;
this.match = match;
}

@Override
public void encode(Codec codec) {
codec.writeVarInt(count);
codec.writeString(match);
}
}
