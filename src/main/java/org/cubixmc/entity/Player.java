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


}
