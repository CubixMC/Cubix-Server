package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutEntityEquipment extends PacketOut {
    private // TODO: Slot item;
    private int entityID;
    private short slot;

    public PacketOutEntityEquipment() {
        super(0x04);
    }

    public PacketOutEntityEquipment(// TODO: Slot item, int entityID, short slot) {
                                    super(0x04);

    this.item=item;
    this.entityID=entityID;
    this.slot=slot;
}

    @Override
    public void encode(Codec codec) {
        codec.writeSlot(item);
        codec.writeVarInt(entityID);
        codec.writeShort(slot);
    }
}
