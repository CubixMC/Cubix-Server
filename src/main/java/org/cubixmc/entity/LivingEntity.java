package org.cubixmc.entity;

public interface LivingEntity extends Entity {

    /**
     * Get the current health of the entity.
     *
     * @return Health of entity
     */
    double getHealth();

    /**
     * Set the current health of the entity.
     * This can not be higher than {#getMaxHealth() getMaxHealth()}.
     *
     * @param health New health for the entity
     */
    void setHealth(double health);

    /**
     * Get the maximum amount of health the entity can have.
     *
     * @return Max health of entity
     */
    double getMaxHealth();
}
