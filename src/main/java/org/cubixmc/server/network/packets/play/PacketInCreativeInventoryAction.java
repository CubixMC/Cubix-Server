package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import lombok.Getter;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInCreativeInventoryAction extends PacketIn {
private // TODO: Slot clickedItem;
private short slot;

public PacketInCreativeInventoryAction() {
super(0x10);
}

@Override
public void decode(Codec codec) {
this.clickedItem = codec.readSlot();
this.slot = codec.readShort();
}

@Override
public void handle() {
}
}
