package org.cubixmc.server.network.packets.play;

import org.cubixmc.inventory.ItemStack;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutSetSlot extends PacketOut {
    private int windowId;
    private short slot;
    private ItemStack item;

    public PacketOutSetSlot() {
        super(0x2F);
    }

    public PacketOutSetSlot(int windowId, short slot, ItemStack item) {
        super(0x2F);
        this.windowId = windowId;
        this.slot = slot;
        this.item = item;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeByte(windowId);
        codec.writeShort(slot);
        codec.writeSlot(item);
    }
}
