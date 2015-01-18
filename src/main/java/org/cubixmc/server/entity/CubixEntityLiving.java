package org.cubixmc.server.entity;

import org.cubixmc.entity.LivingEntity;
import org.cubixmc.server.network.packets.PacketOut;
import org.cubixmc.server.network.packets.play.PacketOutSpawnMob;
import org.cubixmc.server.world.CubixWorld;
import org.cubixmc.util.MathHelper;

public abstract  class CubixEntityLiving extends CubixEntity implements LivingEntity {
    private String customName;
    private boolean customNameVisible;
    private double health;

    protected CubixEntityLiving(CubixWorld world) {
        super(world);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public void setHealth(double health) {
        this.health = health;
    }

    @Override
    public void damageEntity(double damage) {
        this.health = Math.max(0.0, health - damage);
    }

    @Override
    public void setMaxHealth(double maxHealth) {
        // TODO: Remove
    }

    @Override
    public void customName(String name, boolean flag) {
        this.customName = name;
        this.customNameVisible = flag;
    }

    @Override
    public String getCustomName() {
        return customName;
    }

    public PacketOut getSpawnPacket() {
        PacketOutSpawnMob spawn = new PacketOutSpawnMob();
        spawn.setEntityID(entityId);
        spawn.setX(MathHelper.floor(position.getX() * 32.0));
        spawn.setY(MathHelper.floor(position.getY() * 32.0));
        spawn.setZ(MathHelper.floor(position.getZ() * 32.0));
        spawn.setYaw(MathHelper.byteToDegree(position.getYaw()));
        spawn.setPitch(MathHelper.byteToDegree(position.getPitch()));
        spawn.setHeadPitch(MathHelper.byteToDegree(position.getYaw()));
        spawn.setType(0); // TODO: Entity type
        spawn.setMetadata(metadata);
        return spawn;
    }
}
