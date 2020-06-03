package org.cubixmc.entity.other;

import org.cubixmc.entity.Entity;
import org.cubixmc.inventory.Material;

/**
 * org.cubixmc.entity.other Created by Adam on 22/01/15.
 */
public interface FallingBlock extends Entity {
    /**
     * Get the Material of the falling block
     *
     * @return Material of the block
     */
    Material getMaterial();

    /**
     * Get the ID of the falling block
     *
     * @return ID type of the block
     */
    int getBlockId();

    /**
     * Get the data for the falling block
     *
     * @return data of the block
     */
    byte getBlockData();

    /**
     * Get if the falling block will break into an item if it cannot be placed
     *
     * @return true if the block will break into an item when obstructed
     */
    boolean getDropItem();

    /**
     * Set if the falling block will break into an item if it cannot be placed
     *
     * @param drop true to break into an item when obstructed
     */
    void setDropItem(boolean drop);
}