package org.cubixmc.entity;

public interface LivingEntity extends Entity {

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
     * If the developer is adding damage to an entity manually, they would have to use damageEntity();
     * damageEntity(); applies for all entities the void is being called to.
     * @return added Damage to entity
     */
    public void damageEntity(double damage);

    /**
     * Get the maximum amount of health the entity can have.
     *
     * @return Max health of entity
     */
    public double getMaxHealth();
    
    /**
     * Sets the customname of above a mobs head and sets it visible or not 
     *
     * No need for .setCustomNameVisible(true);
     * Thats handled all in one using .customName("name", true/false);
     *
     * @param flag name or not
     */
    public void customName(String name, boolean flag);
}
