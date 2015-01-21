package org.cubixmc.entity;

import org.cubixmc.util.Vector3D;

public interface Vehicle extends Entity {
    /**
     * Gets the vehicle's velocity.
     *
     * @return velocity vector
     */
    public Vector3D getVelocity();

    /**
     * Sets the vehicle's velocity.
     *
     * @param vel velocity vector
     */
    public void setVelocity(Vector3D vel);
}