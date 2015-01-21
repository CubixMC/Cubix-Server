package org.cubixmc.entity.other;

import org.cubixmc.entity.Entity;
import org.cubixmc.inventory.ItemStack;

/**
 * org.cubixmc.entity.other Created by Adam on 22/01/15.
 */
public interface Item extends Entity {
    /**
     * Gets the item stack associated with this item drop.
     *
     * @return An item stack.
     */
    public ItemStack getItemStack();

    /**
     * Sets the item stack associated with this item drop.
     *
     * @param stack An item stack.
     */
    public void setItemStack(ItemStack stack);

    /**
     * Gets the delay before this Item is available to be picked up by players
     *
     * @return Remaining delay
     */
    public int getPickupDelay();

    /**
     * Sets the delay before this Item is available to be picked up by players
     *
     * @param delay New delay
     */
    public void setPickupDelay(int delay);
}