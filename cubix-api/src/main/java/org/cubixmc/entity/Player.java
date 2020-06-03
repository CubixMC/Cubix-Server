package org.cubixmc.entity;

import org.cubixmc.inventory.Inventory;
import org.cubixmc.inventory.PlayerInventory;
import org.cubixmc.GameMode;

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
     * Close inventory.
     */
    void closeInventory();

    /**
     * Check whether or not the player is currently inside of an inventory.
     *
     * @return True if in inventory, false otherwise
     */
    boolean isInsideInventory();

    /**
     * Get the inventory the player is currently in.
     *
     * @return Current inventory
     */
    Inventory getOpenInventory();

    /**
     * @param inventory for the player to open
     */
    void openInventory(Inventory inventory);

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
     * Gets whether the player is sprinting or not.
     *
     * @return true if player is sprinting.
     */
    boolean isSprinting();

    /**
     * Will send a message to the player
     *
     * @param message to the player a message
     */
    void sendMessage(String message);

    /**
     * Sets the player's GameMode
     *
     * @param gameMode the gameMode to set the player to
     */
    void setGameMode(GameMode gameMode);

    /**
     * Gets the player's GameMode
     *
     * @return the player's GameMode
     */
    GameMode getGameMode();

    /**
     * Sets tab header of player
     *
     * @param message Message to be displayed in header
     */
    void setHeader(String message);

    /**
     * Sets tab footer of player
     *
     * @param message Message to be displayed in footer
     */
    void setFooter(String message);
}
