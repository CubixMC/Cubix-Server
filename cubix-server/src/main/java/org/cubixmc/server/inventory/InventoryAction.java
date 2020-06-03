package org.cubixmc.server.inventory;

import org.cubixmc.inventory.ClickType;
import org.cubixmc.inventory.ItemStack;

public enum InventoryAction {
    /**
     * Main inventory actions.
     * This is used to move items around and do basic inventory modifications.
     */
    PICKUP_ALL, // select stack (left click)
    PICKUP_HALF, // select largest half of stack (right click)
    PLACE_ALL, // Place cursor in slot as stack (left click)
    PLACE_ONE, // Place single item from cursor in stack (right click)
    PLACE_SOME, // Fill current item in slot to max stack size (left click)
    SWAP, // Swap cursor item with item in slot
    NOTHING, // When items are of same type but cant stack (left click or right click)

    UNSUPORTED; // Anything we don't support yet

    public static InventoryAction getAction(CubixInventory inventory, ItemStack cursor, ClickType type, int slot) {
        boolean outside = slot < 0; // Slot < 0 means out of window
        ItemStack slotItem = outside ? null : inventory.contents[slot];
        switch(type) {
            case LEFT_CLICK:
                // First we handle pickups
                if(cursor == null) {
                    if(outside || slotItem == null) {
                        return NOTHING; // We clicked an empty slot with an empty cursor
                    }
                    return PICKUP_ALL; // We have an empty cursor and clicked an item, put in cursor
                }

                // Handle drops outside of window
                if(outside) {
                    return UNSUPORTED;
                }

                // Place in empty slot
                if(slotItem == null) {
                    return PLACE_ALL;
                }

                if(slotItem.canStackWith(cursor)) {
                    int allowance = Math.min(slotItem.getType().getMaxStackSize() - slotItem.getAmount(), cursor.getAmount());
                    if(allowance == 0) {
                        return NOTHING; // Do nothing
                    } else if(allowance == cursor.getAmount()) {
                        return PLACE_ALL; // Place all cursor items in slot
                    } else {
                        return PLACE_SOME; // Place one or more items
                    }
                }

                // If cant pickup, drop, place or merge then swap :)
                return SWAP;
            case RIGHT_CLICK:
                if(cursor == null) {
                    if(outside || slotItem == null) {
                        return NOTHING; // Same story
                    }
                    return PICKUP_HALF;
                }

                // Drop single
                if(outside) {
                    return UNSUPORTED;
                }

                if(slotItem == null) {
                    return PLACE_ONE;
                }

                if(slotItem.canStackWith(cursor)) {
                    return slotItem.getAmount() < slotItem.getType().getMaxStackSize() ? PLACE_ONE : NOTHING;
                }

                return SWAP;
            default:
                return UNSUPORTED;
        }
    }
}
