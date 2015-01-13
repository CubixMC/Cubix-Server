package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutSetSlot extends PacketOut {
    private short slot;
    private // TODO: Slot slotData;
    private int windowID;

    public PacketOutSetSlot() {
        super(0x2F);
    }

    public PacketOutSetSlot(short slot// TODO: Slot slotDataint windowID) {
                            super(0x2F);

    this.slot=slot;
    this.slotData=slotData;
    this.windowID=windowID;
}

    @Override
    public void encode(Codec codec) {
        codec.writeShort(slot);
        codec.writeSlot(slotData);
        codec.writeByte(windowID);
    }
}
