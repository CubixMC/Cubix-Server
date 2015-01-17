package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.inventory.ItemStack;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketIn;
import org.cubixmc.util.Position;

@Getter
public class PacketInPlayerBlockPlacement extends PacketIn {
    private Position location;
    private int direction;
    private ItemStack heldItem;
    private int cursorPositionX;
    private int cursorPositionY;
    private int cursorPositionZ;

    public PacketInPlayerBlockPlacement() {
        super(0x08);
    }

    @Override
    public void decode(Codec codec) {
        this.location = codec.readPosition();
        this.direction = codec.readByte();
        this.heldItem = codec.readSlot();
        this.cursorPositionX = codec.readByte();
        this.cursorPositionY = codec.readByte();
        this.cursorPositionZ = codec.readByte();
    }

    @Override
    public void handle(Connection connection) {
    }
}
