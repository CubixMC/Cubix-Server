package org.cubixmc.inventory;

public interface Inventory {


    /**
     * Returns the size of the inventory
     *
     * @return The size of the inventory
     */
    public int getSize();


    /**
     * Returns the name of the inventory
     *
     * @return The String with the name of the inventory
     */
    public String getName();

    /**
     * Clears out the whole Inventory.
     */
    public void clear();

    /**
     * Returns what type of inventory this is.
     *
     * @return The InventoryType representing the type of inventory.
     */
    public InventoryType getType();

    /**
     * Gets the block or entity belonging to the open inventory
     *
     * @return The holder of the inventory; null if it has no holder.
     */
    public InventoryHolder getHolder();
}
