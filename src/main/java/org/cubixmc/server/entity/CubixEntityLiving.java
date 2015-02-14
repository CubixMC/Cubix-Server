package org.cubixmc.server.entity;

import com.google.common.collect.Lists;
import org.cubixmc.entity.Damageable;
import org.cubixmc.entity.Entity;
import org.cubixmc.entity.LivingEntity;
import org.cubixmc.server.network.packets.PacketOut;
import org.cubixmc.server.network.packets.play.PacketOutSpawnMob;
import org.cubixmc.server.world.CubixWorld;
import org.cubixmc.util.MathHelper;

import java.util.List;

public abstract class CubixEntityLiving extends CubixEntity implements LivingEntity, Damageable {
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

    @Override
    public void damage(double amount) {
        damageEntity(amount);
    }

    @Override
    public void damage(double amount, Entity source) {
        damageEntity(amount);
    }

    @Override
    public double getMaxHealth() {
        return 20.0;
    }

    @Override
    public void resetMaxHealth() {
        setHealth(getMaxHealth());
    }

    @Override
    public List<PacketOut> getSpawnPackets() {
        List<PacketOut> list = Lists.newArrayList();
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
        list.add(spawn);
        return list;
    }
}
