package org.cubixmc.inventory;

import org.cubixmc.entity.Entity;
import org.cubixmc.entity.LivingEntity;

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
    String getName();

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
     *
     * @param type The inventory type to be set to
     */
    void setType(InventoryType type);

    /**
     * Gets the block or entity belonging to the open inventory
     *
     * @return The holder of the inventory; null if it has no holder.
     */
    Entity getHolder();
}
