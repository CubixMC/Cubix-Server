package org.cubixmc.entity;

import org.cubixmc.inventory.PlayerInventory;

public interface HumanEntity extends LivingEntity {

    /**
     * Get the name of the player.
     *
     * @return Name of player
     */
    String getName();

    /**
     * Get display name of the player used in chat messages.
     *
     * @return Display name of player
     */
    String getDisplayName();
    
        /**
     * Get the name of the player itself.
     *
     * @return The Player's In-game name.
     */
    String getName();
    
    

    /**
     * Set the display name of the player for chat messages.
     *
     * @param displayName Display name for the player
     */
    void setDisplayName(String displayName);

    /**
     * @return The Player's inventory in inventory form
     */
    PlayerInventory getInventory();
}
