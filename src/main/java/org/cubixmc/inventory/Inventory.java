package org.cubixmc.inventory;

import org.cubixmc.entity.Entity;

public interface Inventory {

    /**
     * Returns the size of the inventory
     *
     * @return The size of the inventory
     */
    int size();

    /**
     * Returns the name of the inventory
     *
     * @return The String with the name of the inventory
     */
    String getTitle();

    /**
     * Clears out the whole Inventory.
     */
    void clear();

    /**
     * Returns what type of inventory this is.
     *
     * @return The InventoryType representing the type of inventory.
     */
    InventoryType getType();

    /**
     * @param type The inventory type to be set to
     */
    void setType(InventoryType type);

    /**
     * Gets the block or entity belonging to the open inventory
     *
     * @return The holder of the inventory; null if it has no holder.
     */
    Entity getHolder();

    /**
     * Get an array of all items inside of inventory.
     *
     * @return Array of items
     */
    ItemStack[] getContents();

    /**
     * Set all contents inside of inventory to array of items.
     *
     * @param contents Array of items
     */
    void setContents(ItemStack[] contents);

    /**
     * Get item from inventory.
     *
     * @param index of item
     * @return Item
     */
    ItemStack getItem(int index);

    /**
     * Set slot in inventory to item.
     *
     * @param index of the slot
     * @param item to put in slot
     */
    void setItem(int index, ItemStack item);

    boolean receive(ItemStack item);

    boolean addItem(ItemStack item);
}
