package org.cubixmc.util;

import org.cubixmc.world.World;

public class Position implements Cloneable {
    private final World world;
    private double x;
    private double y;
    private double z;

    private float yaw;
    private float pitch;

    public Position(World world, double x, double y, double z) {
        this(world, x, y, z, 0F, 0F);
    }

    public Position(World world, double x, double y, double z, float yaw, float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public World getWorld() {
        return world;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public Position subtract(double dx, double dy, double dz) {
        return add(dx * -1, dy * -1, dz * -1);
    }

    public Position add(double dx, double dy, double dz) {
        this.x += dx;
        this.y += dy;
        this.z += dz;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if(Float.compare(position.pitch, pitch) != 0) return false;
        if(Double.compare(position.x, x) != 0) return false;
        if(Double.compare(position.y, y) != 0) return false;
        if(Float.compare(position.yaw, yaw) != 0) return false;
        if(Double.compare(position.z, z) != 0) return false;
        if(!world.equals(position.world)) return false;

        return true;
    }

    /**
     * The real distance between this point and a desired target.
     *
     * @param pos The target
     * @return The real distance in blocks between the two points
     */
    public double distance(Position pos) {
        return Math.sqrt(distanceSquared(pos));
    }

    /**
     * Get the squared distance between this point and a desired target.
     *
     * @param pos The target
     * @return The distance to the power of 2
     */
    public double distanceSquared(Position pos) {
        double x2 = pos.getX();
        double y2 = pos.getY();
        double z2 = pos.getZ();

        double deltaX = x2 - x;
        double deltaY = y2 - y;
        double deltaZ = z2 - z;

        return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
    }

    /**
     * Get a Vector3I for this position.
     *
     * @return A vector with x, y and z as integers
     */
    public Vector3I toVector3I() {
        return new Vector3I(this);
    }

    /**
     * Get a Vector2I for this position.
     *
     * @return A vector with x and z as integers
     */
    public Vector2I toVector2I() {
        return new Vector2I(this);
    }

    /**
     * Get a Vector3F for this position.
     *
     * @return A vector with x, y and z as floats
     */
    public Vector3F toVector3F() {
        return new Vector3F(this);
    }

    /**
     * Get a Vector3D for this position.
     *
     * @return A vector with x, y, and z as doubles
     */
    public Vector3D toVector3D() {
        return new Vector3D(this);
    }

    /**
     * Get a Vector2I representing the chunk coordinates of this position.
     *
     * @return The chunk coordinates of this position
     */
    public Vector2I getChunkCoords() {
        return new Vector2I(MathHelper.floor(x) >> 4, MathHelper.floor(z) >> 4);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = world.hashCode();
        temp = Double.doubleToLongBits(x);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (yaw != +0.0f ? Float.floatToIntBits(yaw) : 0);
        result = 31 * result + (pitch != +0.0f ? Float.floatToIntBits(pitch) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Position{" +
                "world=" + world +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", yaw=" + yaw +
                ", pitch=" + pitch +
                '}';
    }

    @Override
    public Position clone() {
        return new Position(world, x, y, z, yaw, pitch);
    }
}
