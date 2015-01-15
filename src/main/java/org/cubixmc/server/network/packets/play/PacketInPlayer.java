package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import lombok.Getter;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInPlayer extends PacketIn {
private boolean onGround;

public PacketInPlayer() {
super(0x03);
}

@Override
public void decode(Codec codec) {
this.onGround = codec.readBoolean();
}

@Override
public void handle() {
}
}
