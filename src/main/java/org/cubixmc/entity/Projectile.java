package org.cubixmc.entity;

/**
 * org.cubixmc.entity.projectiles Created by Adam on 22/01/15.
 */
public interface Projectile extends Entity {

    /**
     * Retrieve the shooter of this projectile.
     */
    public LivingEntity getShooter();

    /**
     * Set the shooter of this projectile.
     *
     * @param source the {@link LivingEntity} that shot this projectile
     */
    public void setShooter(LivingEntity source);

    /**
     * Determine if this projectile should bounce or not when it hits.
     *
     * @return true if it should bounce.
     */
    public boolean doesBounce();

    /**
     * Set whether or not this projectile should bounce or not when it hits
     * something.
     *
     * @param doesBounce whether or not it should bounce.
     */
    public void setBounce(boolean doesBounce);
}