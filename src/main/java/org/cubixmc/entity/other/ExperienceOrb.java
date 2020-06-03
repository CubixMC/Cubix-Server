package org.cubixmc.entity.other;

import org.cubixmc.entity.Entity;

/**
 * org.cubixmc.entity.other Created by Adam on 22/01/15.
 */
public interface ExperienceOrb extends Entity {
    /**
     * Gets how much experience is contained within this orb
     *
     * @return Amount of experience
     */
    public int getExperience();

    /**
     * Sets how much experience is contained within this orb
     *
     * @param value Amount of experience
     */
    public void setExperience(int value);
}