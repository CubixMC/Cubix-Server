package org.cubixmc.world;

import java.util.UUID;

public interface World {

    /**
     * Get the name of the world.
     *
     * @return Name of world
     */
    String getName();

    /**
     * Get the unique id of the world.
     * This usually doesn't chance.
     *
     * @return Unique id of world
     */
    UUID getUID();
}
