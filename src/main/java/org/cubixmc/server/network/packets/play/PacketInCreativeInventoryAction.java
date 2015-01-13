package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

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

    public void handle() {
    }
}
