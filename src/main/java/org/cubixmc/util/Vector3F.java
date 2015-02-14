package org.cubixmc.util;

import org.cubixmc.world.World;

public class Vector3F {
    private float x;
    private float y;
    private float z;

    /**
     * Construct the vector with all components as 0.
     */
    public Vector3F() {
        this(0, 0, 0);
    }

    /**
     * Construct the vector with all components as 0.
     */
    public Vector3F(Position position) {
        this(position.getX(), position.getY(), position.getZ());
    }

    /**
     * Construct the vector with all components as 0.
     */
    public Vector3F(double x, double y, double z) {
        this((float) x, (float) y, (float) z);
    }

    /**
     * Construct the vector with provided float components.
     *
     * @param x X component
     * @param y Y component
     * @param z Z component
     */
    public Vector3F(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Adds a vector to this one
     *
     * @param vec The other vector
     * @return the same vector
     */
    public Vector3F add(Vector3F vec) {
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
    public Vector3F subtract(Vector3F vec) {
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
    public Vector3F multiply(Vector3F vec) {
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
    public Vector3F divide(Vector3F vec) {
        x /= vec.x;
        y /= vec.y;
        z /= vec.z;
        return this;
    }

    @Override
    public Vector3F clone() {
        return new Vector3F(x, y, z);
    }

    /**
     * Return a block location for this vector.
     *
     * @return An instance of {@link org.cubixmc.util.Vector3I Vector3I}
     */
    public Vector3I toVector3I() {
        return new Vector3I(x, y, z);
    }

    /**
     * Return a block location for this vector.
     *
     * @return An instance of {@link org.cubixmc.util.Vector3D Vector3D}
     */
    public Vector3D toVector3D() {
        return new Vector3D(x, y, z);
    }

    /**
     * Return a position for this vector.
     *
     * @param world Of the position
     * @return An instance of {@link org.cubixmc.world.World World}
     */
    public Position toPositon(World world) {
        return new Position(world, x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector3F vector3F = (Vector3F) o;

        if (Float.compare(vector3F.x, x) != 0) return false;
        if (Float.compare(vector3F.y, y) != 0) return false;
        if (Float.compare(vector3F.z, z) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Vector3F{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}

