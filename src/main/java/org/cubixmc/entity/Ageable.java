package org.cubixmc.entity;

public interface Ageable extends Creature {

    /**
     * Gets the age of this animal.
     *
     * @return Age
     */
    public int getAge();

    /**
     * Sets the age of this animal.
     *
     * @param age New age
     */
    public void setAge(int age);

    /**
     * Sets the age of the animal to a baby
     */
    public void setBaby();

    /**
     * Sets the age of the animal to an adult
     */
    public void setAdult();

    /**
     * Returns true if the animal is an adult.
     *
     * @return return true if the animal is an adult
     */
    public boolean isAdult();


}
