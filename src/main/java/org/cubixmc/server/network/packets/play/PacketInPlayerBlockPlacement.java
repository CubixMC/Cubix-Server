package org.cubixmc.server.network.packets.play;

import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.packets.PacketIn;

public class PacketInPlayerBlockPlacement extends PacketIn {
    private int cursorPositionX;
    private int cursorPositionZ;
    private // TODO: Slot heldItem;
    private int cursorPositionY;
    private Position location;
    private int direction;

    public PacketInPlayerBlockPlacement() {
        super(0x08);
    }

    @Override
    public void decode(Codec codec) {
        this.cursorPositionX = codec.readByte();
        this.cursorPositionZ = codec.readByte();
        this.heldItem = codec.readSlot();
        this.cursorPositionY = codec.readByte();
        this.location = codec.readPosition();
        this.direction = codec.readByte();
    }

    public void handle() {
    }
}
