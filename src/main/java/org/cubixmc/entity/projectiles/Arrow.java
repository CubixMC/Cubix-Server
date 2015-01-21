package org.cubixmc.entity.projectiles;

import org.cubixmc.entity.Projectile;

/**
 * org.cubixmc.entity.projectiles Created by Adam on 22/01/15.
 */
public interface Arrow extends Projectile {
    /**
     * Gets the knockback strength for an arrow, which is the knockback
     * of the bow that shot it.
     *
     * @return the knockback strength value
     */
    public int getKnockbackStrength();

    /**
     * Sets the knockback strength for an arrow.
     *
     * @param knockbackStrength the knockback strength value
     */
    public void setKnockbackStrength(int knockbackStrength);

    /**
     * Gets whether this arrow is critical.ygyg
     *
     * @return true if it is critical
     */
    public boolean isCritical();

    /**
     * Sets whether or not this arrow should be critical.
     *
     * @param critical whether or not it should be critical
     */
    public void setCritical(boolean critical);

}
