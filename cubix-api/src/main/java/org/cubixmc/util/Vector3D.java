package org.cubixmc.util;

import org.bukkit.Location;
import org.bukkit.World;

public class Vector3D {
    private double x;
    private double y;
    private double z;

    /**
     * Construct the vector with all components as 0.
     */
    public Vector3D() {
        this(0, 0, 0);
    }

    /**
     * Construct the vector with all components copied from position.
     */
    public Vector3D(Location location) {
        this(location.getX(), location.getY(), location.getZ());
    }

    /**
     * Construct the vector with provided double components.
     *
     * @param x X component
     * @param y Y component
     * @param z Z component
     */
    public Vector3D(double x, double y, double z) {
        set(x, y, z);
    }

    public void set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public boolean gt(Vector3D vec) {
        return x > vec.x && y > vec.y && z > vec.z;
    }

    public boolean gte(Vector3D vec) {
        return x >= vec.x && y >= vec.y && z >= vec.z;
    }

    public boolean lt(Vector3D vec) {
        return !gte(vec);
    }

    public boolean lte(Vector3D vec) {
        return !gt(vec);
    }

    /**
     * Adds a vector to this one
     *
     * @param vec The other vector
     * @return the same vector
     */
    public Vector3D add(Vector3D vec) {
        x += vec.x;
        y += vec.y;
        z += vec.z;
        return this;
    }

    /**
     * Subtracts a vector from this one.
     *
     * @param vec The other vector
     * @return the same vector
     */
    public Vector3D subtract(Vector3D vec) {
        x -= vec.x;
        y -= vec.y;
        z -= vec.z;
        return this;
    }

    /**
     * Multiplies the vector by another.
     *
     * @param vec The other vector
     * @return the same vector
     */
    public Vector3D multiply(Vector3D vec) {
        x *= vec.x;
        y *= vec.y;
        z *= vec.z;
        return this;
    }

    /**
     * Divides the vector by another.
     *
     * @param vec The other vector
     * @return the same vector
     */
    public Vector3D divide(Vector3D vec) {
        x /= vec.x;
        y /= vec.y;
        z /= vec.z;
        return this;
    }

    @Override
    public Vector3D clone() {
        return new Vector3D(x, y, z);
    }

    /**
     * Return a block location for this vector.
     *
     * @return An instance of {@link Vector3I Vector3I}
     */
    public Vector3I toVector3I() {
        return new Vector3I(x, y, z);
    }

    /**
     * Return a position for this vector.
     *
     * @param world Of the position
     * @return An instance of {@link World World}
     */
    public Location toPositon(World world) {
        return new Location(world, x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Vector3D vector3D = (Vector3D) o;

        if(Double.compare(vector3D.x, x) != 0) return false;
        if(Double.compare(vector3D.y, y) != 0) return false;
        if(Double.compare(vector3D.z, z) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Vector3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
