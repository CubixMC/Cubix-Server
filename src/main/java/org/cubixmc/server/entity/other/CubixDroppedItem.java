package org.cubixmc.server.entity.other;

import com.google.common.collect.Lists;
import org.cubixmc.entity.other.Item;
import org.cubixmc.inventory.ItemStack;
import org.cubixmc.server.entity.CubixEntity;
import org.cubixmc.server.network.packets.PacketOut;
import org.cubixmc.server.network.packets.play.PacketOutEntityMetadata;
import org.cubixmc.server.network.packets.play.PacketOutSpawnMob;
import org.cubixmc.server.network.packets.play.PacketOutSpawnObject;
import org.cubixmc.server.world.CubixWorld;
import org.cubixmc.util.MathHelper;

import java.util.Arrays;
import java.util.List;

public class CubixDroppedItem extends CubixEntity implements Item {

    public CubixDroppedItem(CubixWorld world, ItemStack itemStack) {
        this(world);
        setItemStack(itemStack);
    }

    public CubixDroppedItem(CubixWorld world) {
        super(world);
    }

    @Override
    public List<PacketOut> getSpawnPackets() {
        return Arrays.asList(
                new PacketOutSpawnObject(this, 1),
                new PacketOutEntityMetadata(entityId, metadata)
        );
    }

    @Override
    public ItemStack getItemStack() {
        return metadata.get(10, ItemStack.class);
    }

    @Override
    public void setItemStack(ItemStack stack) {
        metadata.set(10, stack);
    }

    @Override
    public int getPickupDelay() {
        return 0;
    }

    @Override
    public void setPickupDelay(int delay) {
    }
}
