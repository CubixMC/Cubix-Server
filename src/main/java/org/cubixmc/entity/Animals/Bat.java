package org.cubixmc.entity.animals;

import org.cubixmc.entity.Ambient;

public interface Bat extends Ambient {
    /**
     * Checks the current waking state of this bat.
     *
     * @return true if the bat is awake or false if it is currently hanging
     * from a block
     */
    boolean isAwake();

    /**
     * Sets where the bat is awake or not.
     *
     * @param state the new state
     */
    void setAwake(boolean state);
}