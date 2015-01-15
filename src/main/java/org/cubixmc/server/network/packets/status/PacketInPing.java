package org.cubixmc.server.network.packets.status;

import org.cubixmc.server.network.Codec;
import lombok.Getter;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInPing extends PacketIn {
private long time;

public PacketInPing() {
super(0x01);
}

@Override
public void decode(Codec codec) {
this.time = codec.readLong();
}

@Override
public void handle() {
}
}
