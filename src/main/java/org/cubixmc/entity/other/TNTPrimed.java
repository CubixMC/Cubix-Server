package org.cubixmc.entity.other;

import org.cubixmc.entity.Entity;
import org.cubixmc.entity.Explosive;

/**
 * org.cubixmc.entity.other Created by Adam on 22/01/15.
 */
public interface TNTPrimed extends Explosive {
    /**
     * Set the number of ticks until the TNT blows up after being primed.
     *
     * @param fuseTicks The fuse ticks
     */
    public void setFuseTicks(int fuseTicks);

    /**
     * Retrieve the number of ticks until the explosion of this TNTPrimed
     * entity
     *
     * @return the number of ticks until this TNTPrimed explodes
     */
    public int getFuseTicks();

    /**
     * Gets the source of this primed TNT. The source is the entity
     * responsible for the creation of this primed TNT.
     *
     * @return the source of this primed TNT
     */
    public Entity getSource();
}