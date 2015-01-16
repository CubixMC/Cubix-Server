package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

@Getter
public class PacketInConfirmTransaction extends PacketIn {
    private int windowID;
    private short actionNumber;
    private boolean accepted;

    public PacketInConfirmTransaction() {
        super(0x0F);
    }

    @Override
    public void decode(Codec codec) {
        this.windowID = codec.readByte();
        this.actionNumber = codec.readShort();
        this.accepted = codec.readBool();
    }

    @Override
    public void handle() {
    }
}
