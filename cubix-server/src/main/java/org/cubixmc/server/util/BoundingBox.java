package org.cubixmc.server.util;

import org.bukkit.Location;
import org.cubixmc.server.entity.CubixEntity;
import org.cubixmc.util.Vector3D;
import org.cubixmc.util.Vector3F;

public class BoundingBox implements Cloneable {
    private final CubixEntity holder;
    private Vector3D dimensions;
    private Vector3F origin;
    private Vector3D minCoords = new Vector3D();
    private Vector3D maxCoords = new Vector3D();

    public BoundingBox(CubixEntity holder, Vector3D dimensions, Vector3F origin) {
        this.holder = holder;
        this.dimensions = dimensions;
        this.origin = origin;
        update();
    }

    public void update() {
        if(!holder.isSpawned()) return;
        Location pos = holder.getPosition();
        minCoords.set(pos.getX() - origin.getX() * dimensions.getX(), pos.getY() - origin.getY() * dimensions.getY(), pos.getZ() - origin.getZ() * dimensions.getZ());
        maxCoords.set(pos.getX() + (1 - origin.getX()) * dimensions.getX(), pos.getY() + (1 - origin.getY()) * dimensions.getY(), pos.getZ() + (1 - origin.getZ()) * dimensions.getZ());
    }

    public boolean intersects(BoundingBox box) {
        if(box.maxCoords.getX() <= minCoords.getX()) return false; // Left of self
        if(box.maxCoords.getY() <= minCoords.getY()) return false; // Underneath self
        if(box.maxCoords.getZ() <= minCoords.getZ()) return false; // Behind self
        if(box.minCoords.getX() >= maxCoords.getX()) return false; // Right of self
        if(box.minCoords.getY() >= maxCoords.getY()) return false; // Above self
        if(box.minCoords.getZ() >= maxCoords.getZ()) return false; // In front of self
        return true; // Not left, underneath, behind, right, above or in front of self, thus it is intersecting.
    }

    public boolean intersects(Vector3D point) {
        return point.gt(minCoords) && point.lt(maxCoords);
    }

    public void setOrigin(Vector3F origin) {
        this.origin = origin;
    }

    public void grow(double factorX, double factorY, double factorZ) {
        dimensions.multiply(new Vector3D(factorX, factorY, factorZ));
    }

    public void shrink(double factorX, double factorY, double factorZ) {
        dimensions.divide(new Vector3D(factorX, factorY, factorZ));
    }

    public BoundingBox clone() {
        return new BoundingBox(holder, dimensions, origin);
    }
}
