package org.cubixmc.entity;

public enum EntityType {
    DROPPED_ITEM(0x01),
    XP_ORB(0x02),
    LEAD(0x08),
    PAINTING(0x09),
    ITEM_FRAME(0x12),
    ARMOR_STAND(0x1E),
    ENDER_CRYSTAL(0xC8),
    ARROW(0x0A),
    SNOWBALL(0x0B),
    GHAST_FIREBALL(0x0C),
    BLAZE_FIREBALL(0x0D),
    ENDER_PEARL(0x0E),
    EYE_OF_ENDER(0x0f),
    SPLASH_POTION(0x10),
    BOTTLE_ENCHANTING(0x11),
    WITHER_SKULL(0x13),
    FIREWORK(0x16),
    PRIMED_TNT(0x14),
    FALLING_BLOCK(0X15),
    MINECART_COMMAND_BLOCK(0x29),
    BOAT(0x29),
    MINECART(0x2A),
    MINECART_CHEST(0x2B),
    MINECART_FURNACE(0x2C),
    MINECART_TNT(0x2D),
    MINECART_HOPPER(0x2E),
    MINECART_SPAWNER(0x2F),

    //MONSTERS
    CREEPER(0x32, Type.MONSTER),
    SKELETON(0x33, Type.MONSTER),
    SPIDER(0x34, Type.MONSTER),
    GIANT(0x35, Type.MONSTER),
    ZOMBIE(0x36, Type.MONSTER),
    SLIME(0x37, Type.MONSTER),
    GHAST(0x38, Type.MONSTER),
    ZOMBIE_PIGMAN(0x39, Type.MONSTER),
    ENDERMAN(0x3A, Type.MONSTER),
    CAVE_SPIDER(0x3B, Type.MONSTER),
    SILVERFISH(0x3C, Type.MONSTER),
    BLAZE(0x3D, Type.MONSTER),
    MAGMA_CUBE(0x3E, Type.MONSTER),
    ENDER_DRAGON(0x3F, Type.MONSTER),
    WITHER(0x40, Type.MONSTER),
    WITCH(0x42, Type.MONSTER),
    ENDERMITE(0x43, Type.MONSTER),
    GUARDIAN(0x44, Type.MONSTER),
    KILLER_RABBIT(0x65, Type.MONSTER),

    //MOBS
    BAT(0X41, Type.MOB),
    PIG(0X5A, Type.MOB),
    SHEEP(0X5B, Type.MOB),
    COW(0X5C, Type.MOB),
    CHICKEN(0X5D, Type.MOB),
    SQUID(0X5E, Type.MOB),
    WOLF(0X5F, Type.MOB),
    MOOSHROOM(0X60, Type.MOB),
    SNOW_GOLEM(0X61, Type.MOB),
    OCELET(0X62, Type.MOB),
    IRON_GOLEM(0X63, Type.MOB),
    HORSE(0X64, Type.MOB),
    RABBIT(0X65, Type.MOB),
    VILLAGER(0X78, Type.MOB);

    private int typeId;
    private Type type;


    private EntityType(int id, Type type) {
        this.typeId = id;
        this.type = type;
    }

    public int getTypeId() {
        return typeId;
    }

    private EntityType(int id) {
        this.typeId = id;
        this.type = Type.ENTITY;
    }

    public static EntityType getByTypeId(int id) {
        for(int i = 0; i < values().length; i++) {
            EntityType type = values()[i];
            if(type.getTypeId() == id) {
                return type;
            }
        }
        return null;
    }

    public boolean isMonster() {
        if(type.equals(Type.MONSTER)) return true;
        return false;
    }

    public boolean isAnimal() {
        if(type.equals(Type.MOB)) return true;
        return false;
    }

    private enum Type {
        MOB, MONSTER, ENTITY
    }
}
