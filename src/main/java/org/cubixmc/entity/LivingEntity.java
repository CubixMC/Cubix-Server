package org.cubixmc.entity;

public interface LivingEntity extends Entity {

     
    /**
     * Get the entity's name.
     *
     * @return Name of entity
     */
    public String getName();


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
     * Set the maximum amount of health the entity can have.
     *
     * @return Max health of entity
     */
    public void setMaxHealth(doublt maxHealth);
    
    /**
     * @return this.boolean(flag.type, true(), false())
     * @param name New name for the entity 
     * Method does not apply to HumanEntities
     */
    public void setCustomName(String name, boolean flag);
    
    /**
     * @return this.boolean(flag.type, true(), false())
     * @param name New name for the entity 
     * Method does not apply to HumanEntities
     */
    public String getCustomName();
}
