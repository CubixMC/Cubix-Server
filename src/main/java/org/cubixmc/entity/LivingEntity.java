package org.cubixmc.entity;

public interface LivingEntity extends Entity {
 
     /**
     * Get if the living entity is damagable.
     *
     * @return if the entity is damagable
     */
    public boolean isDamagable();

    /**
     * Get the current health of the entity.
     *
     * @return Health of entity
     */
    public double getHealth();

    /**
     * Set the current health of the entity.
     * This can not be higher than {#getMaxHealth() getMaxHealth()}.
     *
     * @param health New health for the entity
     */
    public void setHealth(double health);

    /**
     * Get the maximum amount of health the entity can have.
     *
     * @return Max health of entity
     */
    public double getMaxHealth();
}
