package org.cubixmc.entity;

public interface Player extends LivingEntity {

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
     * Set the display name of the player for chat messages.
     *
     * @param displayName Display name for the player
     */
    void setDisplayName(String displayName);

    /**
     * Send a raw message to the player, will be formatted to json automatically.
     *
     * @param message To send the the player
     */
    void sendMessage(String message);
}
