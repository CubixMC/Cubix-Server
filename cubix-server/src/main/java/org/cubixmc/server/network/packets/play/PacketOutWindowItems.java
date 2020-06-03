package org.cubixmc.server.network.packets.play;

import org.cubixmc.inventory.ItemStack;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

public class PacketOutWindowItems extends PacketOut {
    private int windowId;
    private ItemStack[] items;

    public PacketOutWindowItems() {
        super(0x30);
    }

    public PacketOutWindowItems(int windowId, ItemStack[] items) {
        super(0x30);
        this.windowId = windowId;
        this.items = items;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeByte(windowId);
        codec.writeShort(items.length);
        for(ItemStack item : items) {
            codec.writeSlot(item);
        }
    }
}
