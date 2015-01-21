package org.cubixmc.entity.monsters;

import org.cubixmc.entity.Monster;
import org.cubixmc.inventory.Material;

/**
 * org.cubixmc.entity.monsters Created by Adam on 22/01/15.
 */
public interface Enderman extends Monster {
    /**
     * Get the Materialx of the block that the Enderman is carrying.
     *
     * @return Material containing the id of the block
     */
    public Material getCarriedMaterial();

    /**
     * Get the data of the block that the Enderman is carrying.
     *
     * @return Material containing the id of the block
     */
    public byte getData();

    /**
     * Set the id of the block that the Enderman is carring.
     *
     * @param material to set the carried block to
     */
    public void setCarriedMaterial(Material material);

    /**
     * Set the id of the block that the Enderman is carring.
     *
     * @param material to set the carried block to
     * @param data to set the carried block data to
     */
    public void setCarriedMaterial(Material material, byte data);
}