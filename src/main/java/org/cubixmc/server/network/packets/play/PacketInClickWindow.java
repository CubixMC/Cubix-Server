package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import lombok.Getter;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInClickWindow extends PacketIn {
private int button;
private int mode;
private // TODO: Slot clickedItem;
private short actionNumber;
private short slot;
private int windowID;

public PacketInClickWindow() {
super(0x0E);
}

@Override
public void decode(Codec codec) {
this.button = codec.readByte();
this.mode = codec.readByte();
this.clickedItem = codec.readSlot();
this.actionNumber = codec.readShort();
this.slot = codec.readShort();
this.windowID = codec.readByte();
}

@Override
public void handle() {
}
}
