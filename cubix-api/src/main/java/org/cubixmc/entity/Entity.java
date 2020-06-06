package org.cubixmc.entity;

import org.bukkit.Location;

import java.util.UUID;

public interface Entity {

    /**
     * Get the entity id used by the client.
     *
     * @return Integer id of entity
     */
    int getEntityId();

    /**
     * Get the unique id of the entity used by the server.
     *
     * @return Unique id of entity
     */
    UUID getUniqueId();


    /**
     * @return Position of the player, including x,y,z coords.
     */
    Location getPosition();


    /**
     * Teleports this entity to the given position. Note: If entity is riding a vehicle, not sure if to teleport
     *
     * @param position New location to teleport this entity to
     * @return true if the teleport was successful
     */
    boolean teleport(Location position);


    /**
     * Teleports this entity to the target Entity. If this entity is riding a
     * vehicle, it will be dismounted prior to teleportation.
     *
     * @param target Entity to teleport this entity to
     * @return true if the teleport was successful
     */
    boolean teleport(Entity target);
}
