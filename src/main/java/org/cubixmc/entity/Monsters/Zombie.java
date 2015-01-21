package org.cubixmc.entity.monsters;

import org.cubixmc.entity.Monster;

/**
 * org.cubixmc.entity.monsters Created by Adam on 22/01/15.
 */
public interface Zombie extends Monster {
    /**
     * Gets whether the zombie is a baby
     *
     * @return Whether the zombie is a baby
     */
    public boolean isBaby();

    /**
     * Sets whether the zombie is a baby
     *
     * @param flag Whether the zombie is a baby
     */
    public void setBaby(boolean flag);

    /**
     * Gets whether the zombie is a villager
     *
     * @return Whether the zombie is a villager
     */
    public boolean isVillager();

    /**
     * Sets whether the zombie is a villager
     *
     * @param flag Whether the zombie is a villager
     */
    public void setVillager(boolean flag);
}