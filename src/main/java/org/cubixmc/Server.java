package org.cubixmc;

import java.net.InetAddress;
import java.util.Collection;

public interface Server {

    /**
     * Get the address which the server is running on.
     *
     * @return Socket address of server
     */
    InetAddress getAddress();

    /**
     * Get a collection of all online players.
     *
     * @return All online players
     */
    Collection<?> getOnlinePlayers();

    /**
     * Get the maximum amount of players that can be online on the server.
     *
     * @return Max players on server
     */
    int getMaxPlayers();
}
