package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.inventory.ItemStack;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInCreativeInventoryAction extends PacketIn {
    private short slot;
    private ItemStack clickedItem;

    public PacketInCreativeInventoryAction() {
        super(0x10);
    }

    @Override
    public void decode(Codec codec) {
        this.slot = codec.readShort();
        this.clickedItem = codec.readSlot();
    }

    @Override
    public void handle(Connection connection) {
    }
}
