package com.example.dungeongame.model.weapons;

/**
 * Interface that characterizes an object as being able to be repaired.
 *
 * @author Ethan Nguyen-Tu
 * @version 1.0.0
 */
public interface Repairable {

    /**
     * Method that increases the durability of an object.
     * @param amount int amount that the object's durability can be increased by
     * @return boolean true if the repair succeeds, else false
     */
    boolean repair(int amount);
} // FIN
