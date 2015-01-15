package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutOpenWindow extends PacketOut {
    private int windowId;
    private String inventoryType;
    private String windowTitle;
    private int numberOfSlots;
    private int entityID;

    public PacketOutOpenWindow() {
        super(0x2D);
    }

    public PacketOutOpenWindow(int windowId, String inventoryType, String windowTitle, int numberOfSlots, int entityID) {
        super(0x2D);
        this.windowId = windowId;
        this.inventoryType = inventoryType;
        this.windowTitle = windowTitle;
        this.numberOfSlots = numberOfSlots;
        this.entityID = entityID;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeByte(windowId);
        codec.writeString(inventoryType);
        codec.writeChat(windowTitle);
        codec.writeByte(numberOfSlots);
        codec.writeInt(entityID);
    }
}
