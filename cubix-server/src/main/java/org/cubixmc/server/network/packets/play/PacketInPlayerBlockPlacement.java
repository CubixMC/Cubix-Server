package org.cubixmc.server.network.packets.play;

import lombok.Getter;
import org.bukkit.Location;
import org.cubixmc.inventory.ItemStack;
import org.cubixmc.server.network.Codec;
import org.cubixmc.server.network.Connection;
import org.cubixmc.server.network.packets.PacketIn;
import org.cubixmc.util.Vector3D;

@Getter
public class PacketInPlayerBlockPlacement extends PacketIn {
    private Vector3D location;
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
        Location blockLocation = location.toPositon(connection.getPlayer().getWorld());
        if(direction == 0) blockLocation.add(0, -1, 0);
        else if(direction == 1) blockLocation.add(0, 1, 0);
        else if(direction == 2) blockLocation.add(0, 0, -1);
        else if(direction == 3) blockLocation.add(0, 0, 1);
        else if(direction == 4) blockLocation.add(-1, 0, 0);
        else if(direction == 5) blockLocation.add(1, 0, 0);
        connection.getPlayer().getWorld().getBlock(blockLocation)
                .setTypeAndData(heldItem.getType(), heldItem.getData());
//        connection.getPlayer().getWorld().getChunk(location.getChunkCoords().getX(), location.getChunkCoords().getZ())
//                .setTypeAndData((int) location.getX(), (int) location.getY(), (int) location.getZ(), heldItem.getType(), heldItem.getData());
    }
}
