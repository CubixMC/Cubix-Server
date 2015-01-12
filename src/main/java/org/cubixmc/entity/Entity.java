package org.cubixmc.entity;

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
}
