package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import lombok.Data;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutOpenWindow extends PacketOut {
private String inventoryType;
private String windowTitle;
private int numberOfSlots;
private int entityID;
private int windowId;

public PacketOutOpenWindow() {
super(0x2D);
}

public PacketOutOpenWindow(String inventoryType, String windowTitle, int numberOfSlots, int entityID, int windowId) {
super(0x2D);
this.inventoryType = inventoryType;
this.windowTitle = windowTitle;
this.numberOfSlots = numberOfSlots;
this.entityID = entityID;
this.windowId = windowId;
}

@Override
public void encode(Codec codec) {
codec.writeString(inventoryType);
codec.writeChat(windowTitle);
codec.writeByte(numberOfSlots);
codec.writeInt(entityID);
codec.writeByte(windowId);
}
}
