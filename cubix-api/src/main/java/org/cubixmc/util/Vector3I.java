package org.cubixmc.util;

import org.bukkit.Location;

public class Vector3I {
    private int x;
    private int y;
    private int z;

    public Vector3I(Location location) {
        this(location.getX(), location.getY(), location.getZ());
    }

    public Vector3I(Vector3D location) {
        this(location.getX(), location.getY(), location.getZ());
    }

    public Vector3I(double x, double y, double z) {
        this(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
    }

    public Vector3I(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * Return a Vector3D for the current vector.
     *
     * @return A new instance of {@link Vector3D Vector3D}
     */
    public Vector3D toVector3D() {
        return new Vector3D(x, y, z);
    }

    @Override
    public Vector3I clone() {
        return new Vector3I(x, y, z);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Vector3I vector3I = (Vector3I) o;

        if(x != vector3I.x) return false;
        if(y != vector3I.y) return false;
        if(z != vector3I.z) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }

    @Override
    public String toString() {
        return "Vector3I{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
