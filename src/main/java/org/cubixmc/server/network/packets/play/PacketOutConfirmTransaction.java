package org.cubixmc.server.network.packets.play;

import lombok.Data;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketOut;

@Data
public class PacketOutConfirmTransaction extends PacketOut {
    private int windowID;
    private short actionNumber;
    private boolean accepted;

    public PacketOutConfirmTransaction() {
        super(0x32);
    }

    public PacketOutConfirmTransaction(int windowID, short actionNumber, boolean accepted) {
        super(0x32);
        this.windowID = windowID;
        this.actionNumber = actionNumber;
        this.accepted = accepted;
    }

    @Override
    public void encode(Codec codec) {
        codec.writeByte(windowID);
        codec.writeShort(actionNumber);
        codec.writeBoolean(accepted);
    }
}
