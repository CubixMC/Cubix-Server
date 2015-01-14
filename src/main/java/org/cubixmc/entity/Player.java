package org.cubixmc.entity;

import org.cubixmc.inventory.Inventory;
import org.cubixmc.inventory.PlayerInventory;

import java.net.InetSocketAddress;

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
     * @return The Player's inventory in inventory form
     */
    PlayerInventory getInventory();

    /**
     * Gets the socket address of this player
     *
     * @return the player's address
     */
    InetSocketAddress getAddress();


    /**
     * Kicks player with custom kick message.
     *
     * @param message kick message
     */
    void kickPlayer(String message);


    /**
     * Says a message (or runs a command).
     *
     * @param msg message to print
     */
    void chat(String msg);


    /**
     * Saves the players current information,
     * including; location, health, inventory, motion, and
     * other information into the players data file.
     */
    void saveData();


    /**
     * Loads the players current information,
     * including; location, health, inventory, motion, and
     * other information into the players data file.
     * Note: This should overwrite the players current inventory, health,
     * motion, etc, with the state from the saved data file.
     */
    void loadData();


    /**
     * Makes the player perform the given command
     *
     * @param command Command to perform
     * @return true if the command was successful, otherwise false
     */
    boolean performCommand(String command);

    /**
     * Returns if the player is in sneak mode
     *
     * @return true if player is in sneak mode
     */
    boolean isSneaking();

    /**
     * Sets the sneak mode the player
     *
     * @param sneak true if player should appear sneaking
     */
    void setSneaking(boolean sneak);

    /**
     * Gets whether the player is sprinting or not.
     *
     * @return true if player is sprinting.
     */
    boolean isSprinting();

    /**
     * Sets whether the player is sprinting or not.
     *
     * @param sprinting true if the player should be sprinting
     */
    void setSprinting(boolean sprinting);

    /**
     * Gives the player the amount of experience specified.
     *
     * @param amount Exp amount to give
     */
    void giveExp(int amount);

    /**
     * Gives the player the amount of experience levels specified. Levels can
     * be taken by specifying a negative amount.
     *
     * @param amount amount of experience levels to give or take
     */
    void giveExpLevels(int amount);

    /**
     * Gets the players current experience points towards the next level.
     * <p/>
     * This is a percentage value. 0 is "no progress" and 1 is "next level".
     *
     * @return Current experience points
     */
    float getExp();

    /**
     * Sets the players current experience points towards the next level
     * <p/>
     * This is a percentage value. 0 is "no progress" and 1 is "next level".
     *
     * @param exp New experience points
     */
    void setExp(float exp);

    /**
     * Gets the players current experience level
     *
     * @return Current experience level
     */
    int getLevel();

    /**
     * Sets the players current experience level
     *
     * @param level New experience level
     */
    void setLevel(int level);

    /**
     * Gets the players total experience points
     *
     * @return Current total experience points
     */
    int getTotalExperience();

    /**
     * Sets the players current experience level
     *
     * @param exp New experience level
     */
    void setTotalExperience(int exp);

    /**
     * Will request if the player would like to use the texture pack
     *
     * @param url The url of the pack
     */
    void askResourcePack(String url);

    /**
     * Will force the player to use the texture pack
     *
     * @param url The url of the pack
     */
    void setResourcePack(String url);
    
    /**
     * Will send a message to the player
     *
     * @return to the player a message
    */
    void sendMessage(String message);

    /**
     *
     * @param inventory for the player to open
     */
    void openInventory(Inventory inventory);
}
