package org.cubixmc.entity.monsters;

import org.cubixmc.entity.Monster;

/**
 * org.cubixmc.entity.monsters Created by Adam on 22/01/15.
 */
public interface Creeper extends Monster {

    /**
     * Checks if this Creeper is powered (Electrocuted)
     *
     * @return true if this creeper is powered
     */
    public boolean isPowered();

    /**
     * Sets the Powered status of this Creeper
     *
     * @param value New Powered status
     */
    public void setPowered(boolean value);
}
