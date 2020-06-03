package org.cubixmc.server.inventory;

import org.cubixmc.inventory.ClickType;
import org.cubixmc.inventory.ItemStack;
import org.cubixmc.server.entity.CubixPlayer;
import org.cubixmc.server.network.packets.play.PacketInClickWindow;
import org.cubixmc.server.network.packets.play.PacketOutSetSlot;
import org.cubixmc.server.network.packets.play.PacketOutWindowItems;

/**
 * Container
 *
 * Contains inventory and manages inventory updates.
 * Also manages inventory actions (moving items ect.)
 */
public class Container {
    private final int containerId;
    private final CubixPlayer player;
    private final CubixInventory inventory;
    private int markedUpdateSlot = -1;
    private ItemStack cursor = null;

    public Container(int containerId, CubixPlayer player, CubixInventory inventory) {
        this.containerId = containerId;
        this.player = player;
        this.inventory = inventory;
    }

    /**
     * Perform an inventory action submitted by the client.
     * Note: Also handles action confirmation.
     *
     * @param packet Packet with action information
     * @return True if success, false if otherwise
     */
    public boolean performInventoryAction(PacketInClickWindow packet) {
        ClickType type = ClickType.getById(packet.getMode(), packet.getButton());
        int slot = packet.getSlot();
        ItemStack item = packet.getClickedItem();
        ItemStack actualItem = slot > 0 ? inventory.contents[slot] : null;
        InventoryAction action = InventoryAction.getAction(inventory, cursor, type, slot);
        System.out.println(action);
        switch(action) {
            case PICKUP_ALL:
                if(!verifyItem(slot, item)) return false; // Make sure client isn't lying to us.
                this.cursor = actualItem;
                inventory.contents[slot] = null;
                break;
            case PICKUP_HALF:
                if(!verifyItem(slot, item)) return false;
                int lowerHalf = actualItem.getAmount() / 2;
                ItemStack newCursor = actualItem.clone();
                newCursor.setAmount(newCursor.getAmount() - lowerHalf);
                inventory.contents[slot] = withNewSize(actualItem, lowerHalf);
                this.cursor = newCursor;
                break;
            case PLACE_ALL:
                if(!verifyItem(slot, item)) return false;
                placeInSlot(slot, cursor, -1);
                this.cursor = null;
                break;
            case PLACE_ONE:
                if(!verifyItem(slot, item)) return false;
                placeInSlot(slot, cursor, 1);
                if(cursor.getAmount() == 0) {
                    this.cursor = null;
                }
                break;
            case PLACE_SOME:
                if(!verifyItem(slot, item)) return false;
                placeInSlot(slot, cursor, actualItem.getType().getMaxStackSize() - actualItem.getAmount());
                break;
            case NOTHING:
                // Literally do nothing
                break;
            default:
                return false;
        }
        return true;
    }

    private void placeInSlot(int slot, ItemStack item, int amount) {
        if(inventory.contents[slot] == null) {
            ItemStack is = item.clone();
            if(amount > 0) {
                is.setAmount(amount);
                item.setAmount(item.getAmount() - amount);
            }
            inventory.contents[slot] = is;
        } else {
            int increment = amount > 0 ? amount : item.getAmount(); // Put whole stack unless specific otherwise
            inventory.contents[slot].setAmount(inventory.contents[slot].getAmount() + increment); // update inventory
            if(amount > 0) {
                item.setAmount(item.getAmount() - amount); // Update cursor
            }
        }
    }

    private ItemStack withNewSize(ItemStack item, int newSize) {
        item.setAmount(newSize);
        return newSize <= 0 ? null : item;
    }

    private boolean verifyItem(int index, ItemStack item) {
        if(inventory.contents[index] == null) {
            return item == null;
        }
        return inventory.contents[index].equals(item);
    }

    /**
     * Get current window ID attached to this container.
     *
     * @return ID of container
     */
    public int getWindowId() {
        return containerId;
    }

    /**
     * Get current inventory contained in this container.
     *
     * @return Inventory
     */
    public CubixInventory getInventory() {
        return inventory;
    }

    /**
     * Synchronizes inventory with client if container is marked for update.
     */
    public void update() {
        if(markedUpdateSlot == Integer.MAX_VALUE) {
            sendAll();
        } else if(markedUpdateSlot >= 0) {
            sendOne(markedUpdateSlot);
        }
        this.markedUpdateSlot = -1; // No longer marked for update
    }

    /**
     * Mark container to update single slot in inventory.
     * If container already marked for update, mark for full update.
     *
     * @param index of slot to be updated
     */
    public void markOne(int index) {
        if(markedUpdateSlot == index) return; // Already marked this slot
        this.markedUpdateSlot = markedUpdateSlot >= 0 ? Integer.MAX_VALUE : index;
    }

    /**
     * Mark container to update all slots in inventory
     */
    public void markAll() {
        this.markedUpdateSlot = Integer.MAX_VALUE;
    }

    public void refresh() {
        sendAll();
        player.getConnection().sendPacket(new PacketOutSetSlot(-1, (short) -1, cursor));
    }

    private void sendOne(int index) {
        player.getConnection().sendPacket(new PacketOutSetSlot(containerId, (short) index, inventory.contents[index]));
    }

    private void sendAll() {
        player.getConnection().sendPacket(new PacketOutWindowItems(containerId, inventory.contents));
    }
}
