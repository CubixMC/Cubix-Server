package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import lombok.Getter;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInTabComplete extends PacketIn {
private boolean hasPosition;
private Position lookedAtBlock;
private String text;

public PacketInTabComplete() {
super(0x14);
}

@Override
public void decode(Codec codec) {
this.hasPosition = codec.readBoolean();
this.lookedAtBlock = codec.readPosition();
this.text = codec.readString();
}

@Override
public void handle() {
}
}
