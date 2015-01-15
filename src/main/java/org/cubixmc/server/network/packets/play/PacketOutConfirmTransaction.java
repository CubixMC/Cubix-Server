package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import lombok.Data;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutConfirmTransaction extends PacketOut {
private short actionNumber;
private boolean accepted;
private int windowID;

public PacketOutConfirmTransaction() {
super(0x32);
}

public PacketOutConfirmTransaction(short actionNumber, boolean accepted, int windowID) {
super(0x32);
this.actionNumber = actionNumber;
this.accepted = accepted;
this.windowID = windowID;
}

@Override
public void encode(Codec codec) {
codec.writeShort(actionNumber);
codec.writeBoolean(accepted);
codec.writeByte(windowID);
}
}
