package org.cubixmc.entity;

public interface Damageable extends Entity {
    /**
     * Deals the given amount of damage to this entity.
     *
     * @param amount Amount of damage to deal
     */
    void damage(double amount);

    /**
     * Deals the given amount of damage to this entity, from a specified
     * entity.
     *
     * @param amount Amount of damage to deal
     * @param source Entity which to attribute this damage from
     */
    void damage(double amount, Entity source);

    /**
     * Gets the entity's health from 0 to {@link #getMaxHealth()}, where 0 is dead.
     *
     * @return Health represented from 0 to max
     */
    double getHealth();

    /**
     * Sets the entity's health from 0 to {@link #getMaxHealth()}, where 0 is
     * dead.
     *
     * @param health New health represented from 0 to max
     */
    void setHealth(double health);

    /**
     * Gets the maximum health this entity has.
     *
     * @return Maximum health
     */
    double getMaxHealth();

    /**
     * Sets the maximum health this entity can have.
     *
     * @param health amount of health to set the maximum to
     */
    void setMaxHealth(double health);

    /**
     * Resets the max health to the original amount.
     */
    void resetMaxHealth();
}