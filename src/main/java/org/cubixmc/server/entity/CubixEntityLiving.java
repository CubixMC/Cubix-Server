package org.cubixmc.server.entity;

import org.cubixmc.entity.LivingEntity;
import org.cubixmc.server.world.World;

public abstract  class CubixEntityLiving extends CubixEntity implements LivingEntity {
    private String customName;
    private boolean customNameVisible;
    private double health;

    protected CubixEntityLiving(World world) {
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
}
