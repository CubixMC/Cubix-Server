package org.cubixmc.entity;

public interface Creature extends LivingEntity {

    /**
     * Sets a target for this mob
     *
     * @param target New LivingEntity to target, or null to clear the target
     */
    public void setTarget(LivingEntity target);


    /**
     * Gets the current target of this Creature
     *
     * @return Current target of this creature, or null if none exists
     */
    public LivingEntity getTarget();

}
