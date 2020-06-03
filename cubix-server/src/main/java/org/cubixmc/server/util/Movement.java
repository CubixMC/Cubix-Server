package org.cubixmc.server.util;

import org.cubixmc.server.entity.CubixEntity;
import org.cubixmc.util.MathHelper;
import org.cubixmc.util.Position;
import org.cubixmc.util.Vector3I;

/**
 * Wrapper for fixed-point movement of an entity
 */
public class Movement {
    private final CubixEntity entity;
    private int lastX;
    private int lastY;
    private int lastZ;

    public Movement(CubixEntity entity) {
        this.entity = entity;
    }

    public void reset(Position position) {
        this.lastX = MathHelper.floor(position.getX() * 32.0);
        this.lastY = MathHelper.floor(position.getY() * 32.0);
        this.lastZ = MathHelper.floor(position.getZ() * 32.0);
    }

    public Vector3I update() {
        Position position = entity.getPosition();
        int newX = MathHelper.floor(position.getX() * 32.0);
        int newY = MathHelper.floor(position.getY() * 32.0);
        int newZ = MathHelper.floor(position.getZ() * 32.0);
        Vector3I movement = new Vector3I(newX - lastX, newY - lastY, newZ - lastZ);
        this.lastX = newX;
        this.lastY = newY;
        this.lastZ = newZ;
        return movement;
    }
}
