package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.inventory.ItemStack;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutEntityEquipment extends PacketOut {
    private int entityID;
    private short slot;
    private ItemStack item;

    public PacketOutEntityEquipment() {
        super(0x04);
    }

    public PacketOutEntityEquipment(int entityID, short slot, ItemStack item) {
        super(0x04);
        this.entityID = entityID;
        this.slot = slot;
        this.item = item;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeVarInt(entityID);
        codec.writeShort(slot);
        codec.writeSlot(item);
    }
}
