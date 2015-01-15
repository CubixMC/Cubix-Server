package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInConfirmTransaction extends PacketIn {
    private short actionNumber;
    private boolean accepted;
    private int windowID;

    public PacketInConfirmTransaction() {
        super(0x0F);
    }

    @Override
    public void decode(Codec codec) {
        this.actionNumber = codec.readShort();
        this.accepted = codec.readBoolean();
        this.windowID = codec.readByte();
    }

    @Override
    public void handle() {
    }
}
