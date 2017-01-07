package org.cubixmc.server.entity.other;

import org.cubixmc.entity.other.Item;
import org.cubixmc.inventory.ItemStack;
import org.cubixmc.inventory.PlayerInventory;
import org.cubixmc.server.CubixServer;
import org.cubixmc.server.entity.CubixEntity;
import org.cubixmc.server.entity.CubixPlayer;
import org.cubixmc.server.network.packets.PacketOut;
import org.cubixmc.server.network.packets.play.PacketOutCollectItem;
import org.cubixmc.server.network.packets.play.PacketOutEntityMetadata;
import org.cubixmc.server.network.packets.play.PacketOutSpawnObject;
import org.cubixmc.server.world.CubixWorld;

import java.util.Arrays;
import java.util.List;

public class CubixDroppedItem extends CubixEntity implements Item {
    private int pickupDelay;

    public CubixDroppedItem(CubixWorld world, ItemStack itemStack, int pickupDelay) {
        this(world);
        setItemStack(itemStack);
        setPickupDelay(pickupDelay);
    }

    public CubixDroppedItem(CubixWorld world) {
        super(world);
    }

    @Override
    public void tick() {
        super.tick();
        if(pickupDelay-- > 0) {
            return;
        }

        List<CubixEntity> entities = getNearEntities(1.0);
        for(CubixEntity entity : entities) {
            if(entity instanceof CubixPlayer) {
                CubixPlayer player = (CubixPlayer) entity;
                ItemStack item = getItemStack();
                PlayerInventory inventory = player.getInventory();
                if(inventory.receive(item)) {
                    // Play effect
                    PacketOutCollectItem packet = new PacketOutCollectItem(entityId, player.getEntityId());
                    CubixServer.broadcast(packet, world, null);
                }

                if(item.getAmount() == 0) {
                    // TODO: Mark for removal
                    break;
                }
            }
        }
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
        this.pickupDelay = delay;
    }
}
