package org.cubixmc;

import java.net.InetAddress;
import java.util.Collection;

public interface Server {

    /**
     * Get the address which the server is running on.
     *
     * @return Socket address of server
     */
    public InetAddress getAddress();

    /**
     * Get a collection of all online players.
     *
     * @return All online players
     */
     public Collection<?> getOnlinePlayers();

    /**
     * Get the maximum amount of players that can be online on the server.
     *
     * @return Max players on server
     */
     public int getMaxPlayers();
     
     
    /**
     * Get the server's port
     *
     * @return Server's port
     */
     public int getPort();
}
