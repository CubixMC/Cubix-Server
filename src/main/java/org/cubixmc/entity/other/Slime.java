package org.cubixmc.entity.other;

import org.cubixmc.entity.LivingEntity;

/**
 * org.cubixmc.entity.other Created by Adam on 22/01/15.
 */
public interface Slime extends LivingEntity {
    /**
     * @return The size of the slime
     */
    public int getSize();
    /**
     * @param sz The new size of the slime.
     */
    public void setSize(int sz);
}