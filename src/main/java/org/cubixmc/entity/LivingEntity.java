package org.cubixmc.entity;

public interface LivingEntity extends Entity {

     
    /**
     * Get the entity's name.
     *
     * @return Name of entity
     */
    String getName();


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
     * If the developer is adding damage to an entity manually, they would have to use damageEntity();
     * damageEntity(); applies for all entities the void is being called to.
     */
    void damageEntity(double damage);

    /**
     * Get the maximum amount of health the entity can have.
     *
     * @return Max health of entity
     */
    double getMaxHealth();

    /**
     * Set the maximum amount of health the entity can have.
     *
     * @return Max health of entity
     */
    void setMaxHealth(double maxHealth);
    
    /**
     * @param name New name for the entity
     * Method does not apply to HumanEntities
     */
    void customName(String name, boolean flag);
    
    /**
     * @return this.boolean(flag.type, true(), false())
     * Method does not apply to HumanEntities
     */
    String getCustomName();
}
