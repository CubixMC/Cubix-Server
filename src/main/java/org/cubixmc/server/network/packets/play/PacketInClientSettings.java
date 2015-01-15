package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import lombok.Getter;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInClientSettings extends PacketIn {
private boolean chatColours;
private int chatFlags;
private String locale;
private int viewDistance;
private int displayedSkinParts;

public PacketInClientSettings() {
super(0x15);
}

@Override
public void decode(Codec codec) {
this.chatColours = codec.readBoolean();
this.chatFlags = codec.readByte();
this.locale = codec.readString();
this.viewDistance = codec.readByte();
this.displayedSkinParts = codec.readByte();
}

@Override
public void handle() {
}
}
