package org.cubixmc.util;

import org.bukkit.Location;

public class Vector2I {
    private int x;
    private int z;

    public Vector2I(Location location) {
        this(location.getX(), location.getZ());
    }

    public Vector2I(double x, double z) {
        this(MathHelper.floor(x), MathHelper.floor(z));
    }

    public Vector2I(int x, int z) {
        set(x, z);
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

    public void set(int x, int z) {
        this.x = x;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Vector2I vector2I = (Vector2I) o;

        if(x != vector2I.x) return false;
        if(z != vector2I.z) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + z;
        return result;
    }

    @Override
    public String toString() {
        return "Vector2I{" +
                "x=" + x +
                ", z=" + z +
                '}';
    }
}
