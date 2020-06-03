package org.cubixmc.server.world.lighting;

import com.google.common.collect.Maps;
import org.cubixmc.inventory.Material;
import org.cubixmc.server.util.ListUtil;
import org.cubixmc.server.world.CubixChunk;
import org.cubixmc.server.world.CubixWorld;

import java.util.Map;

public class LightingManager {
    private final Map<Material, Integer> opacityMap = Maps.newConcurrentMap();
    private final CubixWorld world;

    public LightingManager(CubixWorld world) {
        this.world = world;

        // Init block opacity
        for(Material material : Material.values()) {
            opacityMap.put(material, 15); // Deduct all light for most blocks
        }
        // Non-cube transparent items (decrease light value by 1)
        ListUtil.putAll(opacityMap, 1, Material.ANVIL, Material.BED, Material.BREWING_STAND, Material.CAKE, Material.CAULDRON, Material.CHEST, Material.COBBLE_WALL, Material.DAYLIGHT_DETECTOR, Material.INVERT_DAYLIGHT_SENSOR, Material.WOOD_DOOR, Material.IRON_DOOR_BLOCK, Material.ENCHANTMENT_TABLE, Material.ENDER_CHEST, Material.FENCE, Material.FENCE_GATE, Material.HOPPER, Material.IRON_BARDING, Material.LADDER, Material.WATER_LILY, Material.NETHER_FENCE, Material.PISTON_MOVING_PIECE, Material.PISTON_EXTENSION, Material.TRAP_DOOR, Material.IRON_TRAPDOOR, Material.TRAPPED_CHEST, Material.LEAVES, Material.LEAVES_2);
        // Non-solid mechanisms
        ListUtil.putAll(opacityMap, 0, Material.STONE_BUTTON, Material.WOOD_BUTTON, Material.LEVER, Material.WOOD_PLATE, Material.STONE_PLATE, Material.GOLD_PLATE, Material.IRON_PLATE, Material.RAILS, Material.ACTIVATOR_RAIL, Material.POWERED_RAIL, Material.DETECTOR_RAIL, Material.REDSTONE_WIRE, Material.TORCH, Material.REDSTONE_TORCH_ON, Material.REDSTONE_TORCH_OFF);
        // Non-solids
        ListUtil.putAll(opacityMap, 0, Material.AIR, Material.ENDER_PORTAL, Material.FIRE, Material.PORTAL, Material.SIGN_POST, Material.WALL_SIGN, Material.STANDING_BANNER, Material.WALL_BANNER, Material.SNOW);
        // TODO: Plants, fluids and stairs (water deducts 2)
    }

    public int getOpacity(Material type) {
        return opacityMap.get(type);
    }

    /**
     * Init lighting in a chunk.
     *
     * @param chunk The chunk
     * @param soft If false and light was already populated, light will not be recalculated sideways.
     */
    public void initLight(CubixChunk chunk, boolean soft) {
        if(!chunk.isTerrainPopulated()) {
            System.err.println("Terrain not generated");
            return;
//            throw new IllegalStateException("Terrain of " + chunk + " was not populated when server wanted lighting patches!");
        }

        // TODO: Async?
        new InitSkylightTask(this, world, chunk.getPosition(), false).run();
    }
}
