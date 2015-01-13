package org.cubixmc.entity;

import org.cubixmc.util.Position;

import java.util.UUID;

public interface Entity {

    /**
     * Get the entity id used by the client.
     *
     * @return Integer id of entity
     */
    public int getEntityId();

    /**
     * Get the unique id of the entity used by the server.
     *
     * @return Unique id of entity
     */
    public UUID getUniqueId();


    /**
     *
     * @return Position of the player, including x,y,z coords.
     */
    public Position getPosition();


    /**
     * Teleports this entity to the given position. Note: If entity is riding a vehicle, not sure if to teleport

     *
     * @param position New location to teleport this entity to
     * @return true if the teleport was successful
     */
    public boolean teleport(Position position);


    /**
     * Teleports this entity to the given position. Note: If entity is riding a vehicle, not sure if to teleport
     *
     * @param position New position to teleport this entity to
     * @param cause The reason of this teleportation
     * @return true if the teleport was successful
     */
    public boolean teleport(Position position, String cause);


    /**
     * Teleports this entity to the target Entity. If this entity is riding a
     * vehicle, it will be dismounted prior to teleportation.
     *
     * @param destination Entity to teleport this entity to
     * @return true if the teleport was successful
     */
    public boolean teleport(Entity target);


    /**
     * Teleports this entity to the target Entity. If this entity is riding a
     * vehicle, it will be dismounted prior to teleportation.
     *
     * @param destination Entity to teleport this entity to
     * @param cause The reason of this teleportation
     * @return true if the teleport was successful
     */
    public boolean teleport(Entity target, String cause);
}
