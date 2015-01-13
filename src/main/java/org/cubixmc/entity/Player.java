package org.cubixmc.entity;

import java.net.InetSocketAddress;

public interface Player extends HumanEntity {


    /**
     * Gets the socket address of this player
     *
     * @return the player's address
     */
    public InetSocketAddress getAddress();


    /**
     * Kicks player with custom kick message.
     *
     * @param message kick message
     */
    public void kickPlayer(String message);


    /**
     * Says a message (or runs a command).
     *
     * @param msg message to print
     */
    public void chat(String msg);


    /**
     * Saves the players current information,
     * including; location, health, inventory, motion, and
     * other information into the players data file.
     */
    public void saveData();


    /**
     * Loads the players current information,
     * including; location, health, inventory, motion, and
     * other information into the players data file.
     * Note: This should overwrite the players current inventory, health,
     * motion, etc, with the state from the saved data file.
     */
    public void loadData();


    /**
     * Makes the player perform the given command
     *
     * @param command Command to perform
     * @return true if the command was successful, otherwise false
     */
    public boolean performCommand(String command);

    /**
     * Returns if the player is in sneak mode
     *
     * @return true if player is in sneak mode
     */
    public boolean isSneaking();

    /**
     * Sets the sneak mode the player
     *
     * @param sneak true if player should appear sneaking
     */
    public void setSneaking(boolean sneak);

    /**
     * Gets whether the player is sprinting or not.
     *
     * @return true if player is sprinting.
     */
    public boolean isSprinting();

    /**
     * Sets whether the player is sprinting or not.
     *
     * @param sprinting true if the player should be sprinting
     */
    public void setSprinting(boolean sprinting);

    /**
     * Gives the player the amount of experience specified.
     *
     * @param amount Exp amount to give
     */
    public void giveExp(int amount);

    /**
     * Gives the player the amount of experience levels specified. Levels can
     * be taken by specifying a negative amount.
     *
     * @param amount amount of experience levels to give or take
     */
    public void giveExpLevels(int amount);

    /**
     * Gets the players current experience points towards the next level.
     * <p/>
     * This is a percentage value. 0 is "no progress" and 1 is "next level".
     *
     * @return Current experience points
     */
    public float getExp();

    /**
     * Sets the players current experience points towards the next level
     * <p/>
     * This is a percentage value. 0 is "no progress" and 1 is "next level".
     *
     * @param exp New experience points
     */
    public void setExp(float exp);

    /**
     * Gets the players current experience level
     *
     * @return Current experience level
     */
    public int getLevel();

    /**
     * Sets the players current experience level
     *
     * @param level New experience level
     */
    public void setLevel(int level);

    /**
     * Gets the players total experience points
     *
     * @return Current total experience points
     */
    public int getTotalExperience();

    /**
     * Sets the players current experience level
     *
     * @param exp New experience level
     */
    public void setTotalExperience(int exp);

    /**
     * Will request if the player would like to use the texture pack
     *
     * @param url The url of the pack
     */
    public void askResourcePack(String url);


    /**
     * Will force the player to use the texture pack
     *
     * @param url The url of the pack
     */
    public void setResourcePack(String url);
    
    /**
     * Will send a message to the player
     *
     * @return to the player a message
    */
    public void sendMessage(String message);
    
    
}
