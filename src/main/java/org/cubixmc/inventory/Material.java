package org.cubixmc.inventory;

public enum Material {

    /**
     * solid = destroyed by water, opaque = light, block placable
     */
    AIR(0, false, false),
    STONE(1, true, true),
    GRASS(2, true, true),
    DIRT(3, true, true),
    COBBLESTONE(4, true, true),
    WOOD(5, true, true),
    SAPLING(6, false, false),
    BEDROCK(7, true, true),
    WATER(8, true, false),
    STATIONARY_WATER(9, false, false),
    LAVA(10, false, true),
    STATIONARY_LAVA(11, true, true),
    SAND(12, true, true),
    GRAVEL(13, true, true), // Special case of flint drop handled elsewhere
    GOLD_ORE(14, true, true),
    IRON_ORE(15, true, true),
    COAL_ORE(16, true, true),
    LOG(17, true, true),
    LEAVES(18, true, false), // Special case of shears used handled elsewhere
    SPONGE(19, true, true),
    GLASS(20, true, false),
    LAPIS_ORE(21, true, true),
    LAPIS_BLOCK(22, true, true),
    DISPENSER(23, true, true),
    SANDSTONE(24, true, true),
    NOTE_BLOCK(25, true, true),
    BED_BLOCK(26, true, true),
    POWERED_RAIL(27, false, false),
    DETECTOR_RAIL(28, false, false),
    PISTON_STICKY_BASE(29, true, true),
    WEB(30, true, false),
    LONG_GRASS(31, false, false), // Special case of seeds handles elsewhere
    DEAD_BUSH(32, false, false),
    PISTON_BASE(33, true, true),
    PISTON_EXTENSION(34, true, false),
    WOOL(35, true, true),
    PISTON_MOVING_PIECE(36, true, false),
    YELLOW_FLOWER(37, false, false),
    RED_ROSE(38, false, false),
    BROWN_MUSHROOM(39, false, false),
    RED_MUSHROOM(40, false, false),
    GOLD_BLOCK(41, true, true),
    IRON_BLOCK(42, true, true),
    DOUBLE_STEP(43, true, true),
    STEP(44, true, false),
    BRICK(45, true, true),
    TNT(46, true, true),
    BOOKSHELF(47, true, true),
    MOSSY_COBBLESTONE(48, true, true),
    OBSIDIAN(49, true, true),
    TORCH(50, false, false),
    FIRE(51, false, false),
    MOB_SPAWNER(52, true, true),
    WOOD_STAIRS(53, true, false),
    CHEST(54, true, false),
    REDSTONE_WIRE(55, false, false),
    DIAMOND_ORE(56, true, true),
    DIAMOND_BLOCK(57, true, true),
    WORKBENCH(58, true, true),
    CROPS(59, false, false),
    SOIL(60, true, true),
    FURNACE(61, true, true),
    BURNING_FURNACE(62, true, true),
    SIGN_POST(63, true, false),
    WOODEN_DOOR(64, true, false),
    LADDER(65, true, false),
    RAILS(66, false, false),
    COBBLESTONE_STAIRS(67, true, false),
    WALL_SIGN(68, true, false),
    LEVER(69, false, false),
    STONE_PLATE(70, true, false),
    IRON_DOOR_BLOCK(71, true, false),
    WOOD_PLATE(72, true, false),
    REDSTONE_ORE(73, true, true), // IDK if its rly 3-5
    GLOWING_REDSTONE_ORE(74, true, true),
    REDSTONE_TORCH_OFF(75, false, false),
    REDSTONE_TORCH_ON(76, false, false),
    STONE_BUTTON(77, false, false),
    SNOW(78, true, true), // IDK if its rly 1-5
    ICE(79, true, false),
    SNOW_BLOCK(80, true, true), // Still dont know if its true
    CACTUS(81, true, false),
    CLAY(82, true, true), // 1-3 not sure either
    SUGAR_CANE_BLOCK(83, true, false), // 3 not sure
    JUKEBOX(84, true, true),
    FENCE(85, true, false),
    PUMPKIN(86, true, true),
    NETHERRACK(87, true, true),
    SOUL_SAND(88, true, true),
    GLOWSTONE(89, true, true), // IDK again
    PORTAL(90, true, false),
    JACK_O_LANTERN(91, true, true),
    CAKE_BLOCK(92, true, false),
    REPEATER_BLOCK_OFF(93, false, false),
    REPEATER_BLOCK_ON(94, false, false),
    STAINED_GLASS(95, true, false),
    TRAP_DOOR(96, true, false),
    MONSTER_EGGS(97, true, false),
    SMOOTH_BRICK(98, true, true),
    HUGE_MUSHROOM_1(99, true, true),
    HUGE_MUSHROOM_2(100, true, true),
    IRON_FENCE(101, true, false),
    THIN_GLASS(102, true, false),
    MELON_BLOCK(103, true, true), // 1-4 not sure
    PUMPKIN_STEM(104, false, false),
    MELON_STEM(105, false, false),
    VINE(106, false, false),
    FENCE_GATE(107, true, false),
    BRICK_STAIRS(108, true, false),
    SMOOTH_STAIRS(109, true, false),
    MYCEL(110, true, true),
    WATER_LILY(111, true, false),
    NETHER_BRICK(112, true, true),
    NETHER_FENCE(113, true, false),
    NETHER_BRICK_STAIRS(114, true, false),
    NETHER_WARTS(115, false, false), // 1-3 not sure
    ENCHANTMENT_TABLE(116, true, false),
    BREWING_STAND(117, true, false),
    CAULDRON(118, true, false),
    ENDER_PORTAL(119, true, false),
    ENDER_PORTAL_FRAME(120, true, false),
    ENDER_STONE(121, true, true),
    DRAGON_EGG(122, true, false),
    REDSTONE_LAMP_OFF(123, true, true),
    REDSTONE_LAMP_ON(124, true, true),
    WOOD_DOUBLE_STEP(125, true, true),
    WOOD_STEP(126, true, false),
    COCOA(127, false, false),
    SANDSTONE_STAIRS(128, true, false),
    EMERALD_ORE(129, true, true),
    ENDER_CHEST(130, true, false),
    TRIPWIRE_HOOK(131, false, false),
    TRIPWIRE(132, false, false),
    EMERALD_BLOCK(133, true, true),
    SPRUCE_WOOD_STAIRS(134, true, false),
    BIRCH_WOOD_STAIRS(135, true, false),
    JUNGLE_WOOD_STAIRS(136, true, false),
    COMMAND(137, true, true),
    BEACON(138, true, false),
    COBBLE_WALL(139, true, false),
    FLOWER_POT(140, false, false),
    CARROT(141, false, false),
    POTATO(142, false, false),
    WOOD_BUTTON(143, false, false),
    SKULL(144, true, false),
    ANVIL(145, true, false),
    TRAPPED_CHEST(146, true, false),
    GOLD_PLATE(147, true, false),
    IRON_PLATE(148, true, false),
    REDSTONE_COMPARATOR_OFF(149, false, false),
    REDSTONE_COMPARATOR_ON(150, false, false),
    DAYLIGHT_DETECTOR(151, true, true),
    REDSTONE_BLOCK(152, true, true),
    QUARTZ_ORE(153, true, true),
    HOPPER(154, true, true),
    QUARTZ_BLOCK(155, true, true),
    QUARTZ_STAIRS(156, true, false),
    ACTIVATOR_RAIL(157, false, false),
    DROPPER(158, true, true),
    STAINED_CLAY(159, true, true),
    STAINED_GLASS_PANE(160, true, false),
    LEAVES_2(161, true, false),
    LOG_2(162, true, true),
    ACACIA_STAIRS(163, true, false),
    DARK_OAK_STAIRS(164, true, false),
    SLIME_BLOCK(165, true, false),
    BARRIER(166, true, false),
    IRON_TRAPDOOR(167, true, false),
    PRISMARINE(168, true, true),
    SEA_LANTERN(169, false, false),
    HAY_BLOCK(170, true, true),
    CARPET(171, false, false),
    HARD_CLAY(172, true, true),
    COAL_BLOCK(173, true, true),
    PACKED_ICE(174, true, true),
    DOUBLE_PLANT(175, false, false),
    STANDING_BANNER(176, false, false),
    WALL_BANNER(177, false, false),
    INVERT_DAYLIGHT_SENSOR(178, true, false),
    RED_SANDSTONE(179, true, true),
    RED_SANDSTONE_STAIR(180, true, false),
    RED_SANDSTONE_DOUBLESLAB(181, true, false),
    RED_SANDSTONE_SLAB(182, true, false),

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
    PRISMARINE_SHARD(409),
    PRISMARINE_CRYSTALS(410),
    RABBIT(411),
    COOKED_RABBIT(412),
    RABBIT_STEW(413),
    RABBIT_FOOT(414),
    RABBIT_HIDE(415),
    IRON_BARDING(417),
    GOLD_BARDING(418),
    DIAMOND_BARDING(419),
    LEASH(420),
    NAME_TAG(421),
    COMMAND_MINECART(422),
    MUTTON(423),
    COOKED_MUTTON(424),
    BANNER(425),
    SPRUCE_DOOR_ITEM(427),
    BIRCH_DOOR_ITEM(428),
    JUNGLE_DOOR_ITEM(429),
    ACACIA_DOOR_ITEM(430),
    DARK_OAK_DOOR_ITEM(431),
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
    RECORD_12(2267),;

    private final int id, maxStackSize = 64;
    private final boolean solid, opaque, block;

    Material(int id) {
        this(id, false, false, false);
    }

    Material(int id, boolean solid, boolean opaque) {
        this(id, solid, opaque, true);
    }

    Material(int id, boolean solid, boolean opaque, boolean block) {
        this.id = id;
        this.solid = solid;
        this.opaque = opaque;
        this.block = block;
    }

    public boolean isBlock() {
        return block;
    }

    public boolean isOpaque() {
        return opaque;
    }

    public boolean isSolid() {
        return solid;
    }

    public int getId() {
        return id;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    /**
     * Checks if this Material is edible.
     *
     * @return true if this Material is edible.
     */
    public boolean isEdible() {
        switch(this) {
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
        if(!isBlock()) {
            return false;
        }
        switch(this) {
            case SAND:
            case GRAVEL:
            case ANVIL:
                return true;
            default:
                return false;
        }
    }

    public static Material getMaterial(int id) {
        for(Material material : Material.values()) {
            if(material.getId() == id) {
                return material;
            }
        }
        return null;
    }
}
