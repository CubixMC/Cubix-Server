package org.cubixmc.inventory;


public enum InventoryType {
    /**
     * A chest inventory, with 0, 9, 18, 27, 36, 45, or 54 slots of type
     * CONTAINER.
     */
    CHEST("minecraft:chest", 27, "Chest"),
    /**
     * A dispenser inventory, with 9 slots of type CONTAINER.
     */
    DISPENSER("minecraft:dispenser", 9, "Dispenser"),
    /**
     * A dropper inventory, with 9 slots of type CONTAINER.
     */
    DROPPER("minecraft:dropper", 9, "Dropper"),
    /**
     * A furnace inventory, with a RESULT slot, a CRAFTING slot, and a FUEL
     * slot.
     */
    FURNACE("minecraft:furnace", 3, "Furnace"),
    /**
     * A workbench inventory, with 9 CRAFTING slots and a RESULT slot.
     */
    WORKBENCH("minecraft:crafting_table", 10, "Crafting"),
    /**
     * A player's crafting inventory, with 4 CRAFTING slots and a RESULT slot.
     * Also implies that the 4 ARMOR slots are accessible.
     */
    //CRAFTING(5, "Crafting"),
    /**
     * An enchantment table inventory, with one CRAFTING slot and three
     * enchanting buttons.
     */
    ENCHANTING("minecraft:enchanting_table", 1, "Enchanting"),
    /**
     * A brewing stand inventory, with one FUEL slot and three CRAFTING slots.
     */
    BREWING("minecraft:brewing_stand", 4, "Brewing"),
    /**
     * A player's inventory, with 9 QUICKBAR slots, 27 CONTAINER slots, and 4
     * ARMOR slots. The ARMOUR slots may not be visible to the player, though.
     */
    PLAYER(null, 36, "Player"),
    /**
     * The creative mode inventory, with only 9 QUICKBAR slots and nothing
     * else. (The actual creative interface with the items is client-side and
     * cannot be altered by the server.)
     */
    CREATIVE(null, 9, "Creative"),
    /**
     * The merchant inventory, with 2 TRADE-IN slots, and 1 RESULT slot.
     */
    MERCHANT("minecraft:villager", 3, "Villager"),
    /**
     * The ender chest inventory, with 27 slots.
     */
    ENDER_CHEST("minecraft:chest", 27, "Ender Chest"),
    /**
     * An anvil inventory, with 2 CRAFTING slots and 1 RESULT slot
     */
    ANVIL("minecraft:anvil", 3, "Repairing"),
    /**
     * A beacon inventory, with 1 CRAFTING slot
     */
    BEACON("minecraft:beacon", 1, "container.beacon"),
    /**
     * A hopper inventory, with 5 slots of type CONTAINER.
     */
    HOPPER("minecraft:hopper", 5, "Item Hopper"),
    /**
     * Horse inventory
     */
    HORSE("EntityHorse", 0, "Horse");

    private final String networkId;
    private final int size;
    private final String title;

    InventoryType(String networkId, int defaultSize, String defaultTitle) {
        this.networkId = networkId;
        this.size = defaultSize;
        this.title = defaultTitle;
    }

    public String getNetworkId() {
        return networkId;
    }

    public int getDefaultSize() {
        return size;
    }

    public String getDefaultTitle() {
        return title;
    }

    public enum SlotType {
        /**
         * A result slot in a furnace or crafting inventory.
         */
        RESULT,
        /**
         * A slot in the crafting matrix, or the input slot in a furnace
         * inventory, the potion slot in the brewing stand, or the enchanting
         * slot.
         */
        CRAFTING,
        /**
         * An armour slot in the player's inventory.
         */
        ARMOR,
        /**
         * A regular slot in the container or the player's inventory; anything
         * not covered by the other enum values.
         */
        CONTAINER,
        /**
         * A slot in the bottom row or quickbar.
         */
        QUICKBAR,
        /**
         * A pseudo-slot representing the area outside the inventory window.
         */
        OUTSIDE,
        /**
         * The fuel slot in a furnace inventory, or the ingredient slot in a
         * brewing stand inventory.
         */
        FUEL;
    }
}
