package org.cubixmc.entity;

/**
 * CubixCreated by Adam on 13/01/15.
 */
public interface HumanEntity extends LivingEntity {

    /**
     * Get the name of the player.
     *
     * @return Name of player
     */
    public String getName();

    /**
     * Get display name of the player used in chat messages.
     *
     * @return Display name of player
     */
    public String getDisplayName();

    /**
     * Set the display name of the player for chat messages.
     *
     * @param displayName Display name for the player
     */
    public void setDisplayName(String displayName);

    /**
     * Send a raw message to the player, will be formatted to json automatically.
     *
     * @param message To send the the player
     */
    public void sendMessage(String message);

    /**
     * @return Health of player
     */
    public double getHealth();

    /**
     * @param health Desired health of player
     */
    public void setHealth(int health);

}
