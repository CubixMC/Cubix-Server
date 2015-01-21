package org.cubixmc.entity.animals;

import org.cubixmc.entity.Golem;

/**
 * org.cubixmc.entity.animals Created by Adam on 22/01/15.
 */
public interface IronGolem extends Golem {
    /**
     * Gets whether this iron golem was built by a player.
     *
     * @return Whether this iron golem was built by a player
     */
    public boolean isPlayerCreated();

    /**
     * Sets whether this iron golem was built by a player or not.
     *
     * @param playerCreated true if you want to set the iron golem as being
     *                      player created, false if you want it to be a natural village golem.
     */
    public void setPlayerCreated(boolean playerCreated);
}