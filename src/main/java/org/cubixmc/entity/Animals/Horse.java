package org.cubixmc.entity.animals;

import org.cubixmc.entity.Animal;
import org.cubixmc.entity.Vehicle;
import org.cubixmc.inventory.Inventory;

/**
 * org.cubixmc.entity.animals Created by Adam on 22/01/15.
 */
public interface Horse extends Animal, Vehicle {

    /**
     * Represents the different types of horses that may exist.
     */
    public enum Variant {
        /**
         * A normal horse
         */
        HORSE,
        /**
         * A donkey
         */
        DONKEY,
        /**
         * A mule
         */
        MULE,
        /**
         * An undead horse
         */
        UNDEAD_HORSE,
        /**
         * A skeleton horse
         */
        SKELETON_HORSE,;
    }

    /**
     * Represents the base color that the horse has.
     */
    public enum Color {
        /**
         * Snow white
         */
        WHITE,
        /**
         * Very light brown
         */
        CREAMY,
        /**
         * Chestnut
         */
        CHESTNUT,
        /**
         * Light brown
         */
        BROWN,
        /**
         * Pitch black
         */
        BLACK,
        /**
         * Gray
         */
        GRAY,
        /**
         * Dark brown
         */
        DARK_BROWN,;
    }

    /**
     * Represents the style, or markings, that the horse has.
     */
    public enum Style {
        /**
         * No markings
         */
        NONE,
        /**
         * White socks or stripes
         */
        WHITE,
        /**
         * Milky splotches
         */
        WHITEFIELD,
        /**
         * Round white dots
         */
        WHITE_DOTS,
        /**
         * Small black dots
         */
        BLACK_DOTS,;
    }

    /**
     * Gets the horse's variant.
     *
     * @return a {@link Variant} representing the horse's variant
     */
    public Variant getVariant();

    /**
     * Sets the horse's variant.
     *
     * @param variant a {@link Variant} for this horse
     */
    public void setVariant(Variant variant);

    /**
     * Gets the horse's color
     *
     * @return a {@link Color} representing the horse's group
     */
    public Color getColor();

    /**
     * Sets the horse's color.
     *
     * @param color a {@link Color} for this horse
     */
    public void setColor(Color color);

    /**
     * Gets the horse's style.
     *
     * @return a {@link Style} representing the horse's style
     */
    public Style getStyle();

    /**
     * Sets the style of this horse.
     *
     * @param style a {@link Style} for this horse
     */
    public void setStyle(Style style);

    /**
     * Gets whether the horse has a chest equipped.
     *
     * @return true if the horse has chest storage
     */
    public boolean isCarryingChest();

    /**
     * Sets whether the horse has a chest equipped.
     * Removing a chest will also clear the chest's inventory.
     *
     * @param chest true if the horse should have a chest
     */
    public void setCarryingChest(boolean chest);


    /**
     * Gets the jump strength of this horse.
     *
     * @return the horse's jump strength
     */
    public double getJumpStrength();

    /**
     * Sets the jump strength of this horse.
     *
     * @param strength jump strength for this horse
     */
    public void setJumpStrength(double strength);

    public Inventory getInventory();

}
