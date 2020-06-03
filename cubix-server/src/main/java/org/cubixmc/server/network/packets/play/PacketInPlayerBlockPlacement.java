package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.cubixmc.inventory.ItemStack;
import org.cubixmc.server.CubixServer;
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
        System.out.println("place @" + location + " block " + heldItem);
        if(direction == 0) location.add(0, -1, 0);
        if(direction == 1) location.add(0, 1, 0);
        if(direction == 2) location.add(0, 0, -1);
        if(direction == 3) location.add(0, 0, 1);
        if(direction == 4) location.add(-1, 0, 0);
        if(direction == 5) location.add(1, 0, 0);
        connection.getPlayer().getWorld().getBlock(location)
                .setTypeAndData(heldItem.getType(), heldItem.getData());
//        connection.getPlayer().getWorld().getChunk(location.getChunkCoords().getX(), location.getChunkCoords().getZ())
//                .setTypeAndData((int) location.getX(), (int) location.getY(), (int) location.getZ(), heldItem.getType(), heldItem.getData());
    }
}