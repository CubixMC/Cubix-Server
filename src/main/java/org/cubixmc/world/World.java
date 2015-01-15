package org.cubixmc.world;

import org.cubixmc.entity.Entity;

import java.util.List;
import java.util.UUID;

public interface World {

    /**
     * Get the name of the world.
     *
     * @return Name of world
     */
    public String getName();

    /**
     * Get the unique id of the world.
     * This usually doesn't chance.
     *
     * @return Unique id of world
     */
     public UUID getUUID();

    /**
     * Get the seed for the
     * current world.
     *
     * @return Long seed
     */
     public long getSeed();

    /**
     * Get a list of all entities
     * for the current world.
     *
     * @return List from world
     */
     public List<Entity> getEntities();

    /**
     * Get an array of all entities
     * for the current world.
     *
     * @return Entity[] from world
     */
     //public Entity[] getEntities();
}
