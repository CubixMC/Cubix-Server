package org.cubixmc.entity.animals;

import org.cubixmc.entity.Animal;

/**
 * org.cubixmc.entity.animals Created by Adam on 22/01/15.
 */
public interface Sheep extends Animal {
    /**
     * @return Whether the sheep is sheared.
     */
    public boolean isSheared();

    /**
     * @param flag Whether to shear the sheep
     */
    public void setSheared(boolean flag);
}