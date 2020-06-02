package org.cubixmc.inventory;


public interface PlayerInventory extends Inventory {

    /**
     * Set helmet slot to item.
     *
     * @param helmet Item to set as helmet
     */
    void setHelmet(ItemStack helmet);

    /**
     * Set chestplate slot to item.
     *
     * @param chestplate Item to set as chestplate
     */
    void setChestplate(ItemStack chestplate);

    /**
     * Set leggings slot to item.
     *
     * @param leggings Item to set as leggings
     */
    void setLeggings(ItemStack leggings);

    /**
     * Set boots slot to item.
     *
     * @param boots Item to set as boots
     */
    void setBoots(ItemStack boots);

    /**
     * Get the item in the inventory helmet slot.
     *
     * @return Helmet item
     */
    ItemStack getHelmet();

    /**
     * Get the item in the inventory chestplate slot.
     *
     * @return Chestplate item
     */
    ItemStack getChestplate();

    /**
     * Get the item in the inventory leggings slot.
     *
     * @return Leggings item
     */
    ItemStack getLeggings();

    /**
     * Get the item in the inventory boots slot.
     *
     * @return Boots item
     */
    ItemStack getBoots();
}
