package org.cubixmc.entity;

import org.cubixmc.entity.Explosive;
import org.cubixmc.entity.Projectile;
import org.cubixmc.util.Vector3D;

/**
 * org.cubixmc.entity.projectiles Created by Adam on 22/01/15.
 */
public interface Fireball extends Projectile, Explosive {
    /**
     * Fireballs fly straight and do not take setVelocity(...) well.
     *
     * @param direction the direction this fireball is flying toward
     */
    public void setDirection(Vector3D direction);

    /**
     * Retrieve the direction this fireball is heading toward
     *
     * @return the direction
     */
    public Vector3D getDirection();
}