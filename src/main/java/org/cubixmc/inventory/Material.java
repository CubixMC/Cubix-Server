package org.cubixmc.inventory;

import com.google.common.collect.Maps;
import java.lang.reflect.Constructor;
import java.util.Map;


public enum Material {


    /**
     * solid = destroyed by water, opaque = light, block placable
     */
    AIR(0, false, false, true),
    STONE(1, true, true, true),
    GRASS(2, true, true, true),
    DIRT(3, true, true, true),
    COBBLESTONE(4, true, true, true),
    WOOD(5, true, true, true),
    SAPLING(6, false, false, true),
    BEDROCK(7, true, true, true),
    WATER(8, true, false, true),
    STATIONARY_WATER(9, false, false, true),
    LAVA(10, false, true, true),
    STATIONARY_LAVA(11, true, true, true),
    SAND(12, true, true, true),
    GRAVEL(13, true, true, true),
    GOLD_ORE(14, true, true, true),
    IRON_ORE(15, true, true, true),
    COAL_ORE(16, true, true, true),
    LOG(17, true, true, true),
    LEAVES(18, true, false, true),
    SPONGE(19, true, true, true),
    GLASS(20, true, false, true),
    LAPIS_ORE(21, true, true, true),
    LAPIS_BLOCK(22, true, true, true),
    DISPENSER(23, true, true, true),
    SANDSTONE(24, true, true, true),
    NOTE_BLOCK(25, true, true, true),
    BED_BLOCK(26, true, true, true),
    POWERED_RAIL(27, false, false, true),
    DETECTOR_RAIL(28, false, false, true),
    PISTON_STICKY_BASE(29, true, true, true),
    WEB(30, true, false, true),
    LONG_GRASS(31, false, false, true),
    DEAD_BUSH(32, false, false, true),
    PISTON_BASE(33, true, true, true),
    PISTON_EXTENSION(34, true, false, true),
    WOOL(35, true, true, true),
    PISTON_MOVING_PIECE(36, true, false, true),
    YELLOW_FLOWER(37, false, false, true),
    RED_ROSE(38, false, false, true),
    BROWN_MUSHROOM(39, false, false, true),
    RED_MUSHROOM(40, false, false, true),
    GOLD_BLOCK(41, true, true, true),
    IRON_BLOCK(42),
    DOUBLE_STEP(43, true, true, true),
    STEP(44, true, false, true),
    BRICK(45, true, true, true),
    TNT(46, true, true, true),
    BOOKSHELF(47, true, true, true),
    MOSSY_COBBLESTONE(48, true, true, true),
    OBSIDIAN(49, true, true, true),
    TORCH(50, false, false, true),
    FIRE(51, false, false, true),
    MOB_SPAWNER(52, true, true, true),
    WOOD_STAIRS(53, true, false, true),
    CHEST(54, true, false, true),
    REDSTONE_WIRE(55, false, false, true),
    DIAMOND_ORE(56, true, true, true),
    DIAMOND_BLOCK(57, true, true, true),
    WORKBENCH(58, true, true, true),
    CROPS(59, false, false, true),
    SOIL(60, true, true, true),
    FURNACE(61, true, true, true),
    BURNING_FURNACE(62, true, true, true),
    SIGN_POST(63, true, false, true),
    WOODEN_DOOR(64, true, false, true),
    LADDER(65, true, false, true),
    RAILS(66, false, false, true),
    COBBLESTONE_STAIRS(67, true, false, true),
    WALL_SIGN(68, true, false, true),
    LEVER(69, false, false, true),
    STONE_PLATE(70, true, false, true),
    IRON_DOOR_BLOCK(71, true, false, true),
    WOOD_PLATE(72, true, false, true),
    REDSTONE_ORE(73, true, true, true),
    GLOWING_REDSTONE_ORE(74, true, true, true),
    REDSTONE_TORCH_OFF(75, false, false, true),
    REDSTONE_TORCH_ON(76, false, false, true),
    STONE_BUTTON(77, false, false, true),
    SNOW(78, true, true, true),
    ICE(79, true, false, true),
    SNOW_BLOCK(80, true, true, true),
    CACTUS(81, true, false, true),
    CLAY(82, true, true, true),
    SUGAR_CANE_BLOCK(83, true, false, true),
    JUKEBOX(84, true, true, true),
    FENCE(85, true, false, true),
    PUMPKIN(86, true, true, true),
    NETHERRACK(87, true, true, true),
    SOUL_SAND(88, true, true, true),
    GLOWSTONE(89, true, true, true),
    PORTAL(90, true, false, true),
    JACK_O_LANTERN(91, true, true, true),
    CAKE_BLOCK(92, true, false, true),
    REPEATER_BLOCK_OFF(93, false, false, true),
    REPEATER_BLOCK_ON(94, false, false, true),
    STAINED_GLASS(95, true, false, true),
    TRAP_DOOR(96, true, false, true),
    MONSTER_EGGS(97),
    SMOOTH_BRICK(98, true, true, true),
    HUGE_MUSHROOM_1(99, true, true, true),
    HUGE_MUSHROOM_2(100, true, true, true),
    IRON_FENCE(101, true, false, true),
    THIN_GLASS(102, true, false, true),
    MELON_BLOCK(103, true, true, true),
    PUMPKIN_STEM(104, false, false, true),
    MELON_STEM(105, false, false, true),
    VINE(106, false, false, true),
    FENCE_GATE(107, true, false, true),
    BRICK_STAIRS(108, true, false, true),
    SMOOTH_STAIRS(109, true, false, true),
    MYCEL(110, true, true, true),
    WATER_LILY(111, true, false, true),
    NETHER_BRICK(112, true, true, true),
    NETHER_FENCE(113, true, false, true),
    NETHER_BRICK_STAIRS(114, true, false, true),
    NETHER_WARTS(115, false, false, true),
    ENCHANTMENT_TABLE(116, true, false, true),
    BREWING_STAND(117, true, false, true),
    CAULDRON(118, true, false, true),
    ENDER_PORTAL(119, true, false, true),
    ENDER_PORTAL_FRAME(120, true, false, true),
    ENDER_STONE(121, true, true, true),
    DRAGON_EGG(122, true, false, true),
    REDSTONE_LAMP_OFF(123, true, true, true),
    REDSTONE_LAMP_ON(124, true, true, true),
    WOOD_DOUBLE_STEP(125, true, true, true),
    WOOD_STEP(126, true, false, true),
    COCOA(127, false, false, true),
    SANDSTONE_STAIRS(128, true, false, true),
    EMERALD_ORE(129, true, true, true),
    ENDER_CHEST(130, true, false, true),
    TRIPWIRE_HOOK(131, false, false, true),
    TRIPWIRE(132, false, false, true),
    EMERALD_BLOCK(133, true, true, true),
    SPRUCE_WOOD_STAIRS(134, true, false, true),
    BIRCH_WOOD_STAIRS(135, true, false, true),
    JUNGLE_WOOD_STAIRS(136, true, false, true),
    COMMAND(137, true, true, true),
    BEACON(138, true, false, true),
    COBBLE_WALL(139, true, false, true),
    FLOWER_POT(140, false, false, true),
    CARROT(141, false, false, true),
    POTATO(142, false, false, true),
    WOOD_BUTTON(143, false, false, true),
    SKULL(144, true, false, true),
    ANVIL(145, true, false, true),
    TRAPPED_CHEST(146, true, false, true),
    GOLD_PLATE(147, true, false, true),
    IRON_PLATE(148, true, false, true),
    REDSTONE_COMPARATOR_OFF(149, false, false, true),
    REDSTONE_COMPARATOR_ON(150, false, false, true),
    DAYLIGHT_DETECTOR(151, true, true, true),
    REDSTONE_BLOCK(152, true, true, true),
    QUARTZ_ORE(153, true, true, true),
    HOPPER(154, true, true, true),
    QUARTZ_BLOCK(155, true, true, true),
    QUARTZ_STAIRS(156, true, false, true),
    ACTIVATOR_RAIL(157, false, false, true),
    DROPPER(158, true, true, true),
    STAINED_CLAY(159, true, true, true),
    STAINED_GLASS_PANE(160, true, false, true),
    LEAVES_2(161, true, false, true),
    LOG_2(162, true, true, true),
    ACACIA_STAIRS(163, true, false, true),
    DARK_OAK_STAIRS(164, true, false, true),
    HAY_BLOCK(170, true, true, true),
    CARPET(171, false, false, true),
    HARD_CLAY(172, true, true, true),
    COAL_BLOCK(173, true, true, true),
    PACKED_ICE(174, true, true, true),
    DOUBLE_PLANT(175, false, false, true),
    // ----- Item Separator -----
    IRON_SPADE(256),
    IRON_PICKAXE(257),
    IRON_AXE(258),
    FLINT_AND_STEEL(259),
    APPLE(260),
    BOW(261),
    ARROW(262),
    COAL(263),
    DIAMOND(264),
    IRON_INGOT(265),
    GOLD_INGOT(266),
    IRON_SWORD(267),
    WOOD_SWORD(268),
    WOOD_SPADE(269),
    WOOD_PICKAXE(270),
    WOOD_AXE(271),
    STONE_SWORD(272),
    STONE_SPADE(273),
    STONE_PICKAXE(274),
    STONE_AXE(275),
    DIAMOND_SWORD(276),
    DIAMOND_SPADE(277),
    DIAMOND_PICKAXE(278),
    DIAMOND_AXE(279),
    STICK(280),
    BOWL(281),
    MUSHROOM_SOUP(282),
    GOLD_SWORD(283),
    GOLD_SPADE(284),
    GOLD_PICKAXE(285),
    GOLD_AXE(286),
    STRING(287),
    FEATHER(288),
    SULPHUR(289),
    WOOD_HOE(290),
    STONE_HOE(291),
    IRON_HOE(292),
    DIAMOND_HOE(293),
    GOLD_HOE(294),
    SEEDS(295),
    WHEAT(296),
    BREAD(297),
    LEATHER_HELMET(298),
    LEATHER_CHESTPLATE(299),
    LEATHER_LEGGINGS(300),
    LEATHER_BOOTS(301),
    CHAINMAIL_HELMET(302),
    CHAINMAIL_CHESTPLATE(303),
    CHAINMAIL_LEGGINGS(304),
    CHAINMAIL_BOOTS(305),
    IRON_HELMET(306),
    IRON_CHESTPLATE(307),
    IRON_LEGGINGS(308),
    IRON_BOOTS(309),
    DIAMOND_HELMET(310),
    DIAMOND_CHESTPLATE(311),
    DIAMOND_LEGGINGS(312),
    DIAMOND_BOOTS(313),
    GOLD_HELMET(314),
    GOLD_CHESTPLATE(315),
    GOLD_LEGGINGS(316),
    GOLD_BOOTS(317),
    FLINT(318),
    PORK(319),
    GRILLED_PORK(320),
    PAINTING(321),
    GOLDEN_APPLE(322),
    SIGN(323),
    WOOD_DOOR(324),
    BUCKET(325),
    WATER_BUCKET(326),
    LAVA_BUCKET(327),
    MINECART(328),
    SADDLE(329),
    IRON_DOOR(330),
    REDSTONE(331),
    SNOW_BALL(332),
    BOAT(333),
    LEATHER(334),
    MILK_BUCKET(335),
    CLAY_BRICK(336),
    CLAY_BALL(337),
    SUGAR_CANE(338),
    PAPER(339),
    BOOK(340),
    SLIME_BALL(341),
    STORAGE_MINECART(342),
    POWERED_MINECART(343),
    EGG(344),
    COMPASS(345),
    FISHING_ROD(346),
    WATCH(347),
    GLOWSTONE_DUST(348),
    RAW_FISH(349),
    COOKED_FISH(350),
    INK_SACK(351),
    BONE(352),
    SUGAR(353),
    CAKE(354),
    BED(355),
    REPEATER(356),
    COOKIE(357),

    MAP(358),
    SHEARS(359),
    MELON(360),
    PUMPKIN_SEEDS(361),
    MELON_SEEDS(362),
    RAW_BEEF(363),
    COOKED_BEEF(364),
    RAW_CHICKEN(365),
    COOKED_CHICKEN(366),
    ROTTEN_FLESH(367),
    ENDER_PEARL(368),
    BLAZE_ROD(369),
    GHAST_TEAR(370),
    GOLD_NUGGET(371),
    NETHER_STALK(372),

    POTION(373),
    GLASS_BOTTLE(374),
    SPIDER_EYE(375),
    FERMENTED_SPIDER_EYE(376),
    BLAZE_POWDER(377),
    MAGMA_CREAM(378),
    BREWING_STAND_ITEM(379),
    CAULDRON_ITEM(380),
    EYE_OF_ENDER(381),
    SPECKLED_MELON(382),
    MONSTER_EGG(383),
    EXP_BOTTLE(384),
    FIREBALL(385),
    BOOK_AND_QUILL(386),
    WRITTEN_BOOK(387),
    EMERALD(388),
    ITEM_FRAME(389),
    FLOWER_POT_ITEM(390),
    CARROT_ITEM(391),
    POTATO_ITEM(392),
    BAKED_POTATO(393),
    POISONOUS_POTATO(394),
    EMPTY_MAP(395),
    GOLDEN_CARROT(396),
    SKULL_ITEM(397),
    CARROT_STICK(398),
    NETHER_STAR(399),
    PUMPKIN_PIE(400),
    FIREWORK(401),
    FIREWORK_CHARGE(402),
    ENCHANTED_BOOK(403),
    REDSTONE_COMPARATOR(404),
    NETHER_BRICK_ITEM(405),
    QUARTZ(406),
    EXPLOSIVE_MINECART(407),
    HOPPER_MINECART(408),
    IRON_BARDING(417),
    GOLD_BARDING(418),
    DIAMOND_BARDING(419),
    LEASH(420),
    NAME_TAG(421),
    COMMAND_MINECART(422),
    GOLD_RECORD(2256),
    GREEN_RECORD(2257),
    RECORD_3(2258),
    RECORD_4(2259),
    RECORD_5(2260),
    RECORD_6(2261),
    RECORD_7(2262),
    RECORD_8(2263),
    RECORD_9(2264),
    RECORD_10(2265),
    RECORD_11(2266),
    RECORD_12(2267);

    private final int id;
    private final boolean solid, opaque, block;

    /**
     * solid = placable, opaque = light, block placable
     */
    private Material(int id, boolean solid, boolean opaque, boolean block){
        this.id = id;
        this.solid= solid;
        this.opaque = opaque;
        this.block = block;
    }
    private Material(int id){
        this(id, false, false, false);
    }

    public static Material getMaterial(int id){
        for(Material material : Material.values())
            if (material.getId() == id) {
                return material;
            }
        return null;
    }

    public boolean isBlock(){
        return block;
    }

    public boolean isOpaque(){
        return opaque;
    }

    public boolean isSolid(){
        return solid;
    }

    public int getId(){
        return id;
    }

    /**
     * Checks if this Material is edible.
     *
     * @return true if this Material is edible.
     */
    public boolean isEdible() {
        switch (this) {
            case BREAD:
            case CARROT_ITEM:
            case BAKED_POTATO:
            case POTATO_ITEM:
            case POISONOUS_POTATO:
            case GOLDEN_CARROT:
            case PUMPKIN_PIE:
            case COOKIE:
            case MELON:
            case MUSHROOM_SOUP:
            case RAW_CHICKEN:
            case COOKED_CHICKEN:
            case RAW_BEEF:
            case COOKED_BEEF:
            case RAW_FISH:
            case COOKED_FISH:
            case PORK:
            case GRILLED_PORK:
            case APPLE:
            case GOLDEN_APPLE:
            case ROTTEN_FLESH:
            case SPIDER_EYE:
                return true;
            default:
                return false;
        }
    }

    /**
     * @return True if this material is affected by gravity.
     */
    public boolean hasGravity() {
        if (!isBlock()) {
            return false;
        }
        switch (this) {
            case SAND:
            case GRAVEL:
            case ANVIL:
                return true;
            default:
                return false;
        }
    }
}