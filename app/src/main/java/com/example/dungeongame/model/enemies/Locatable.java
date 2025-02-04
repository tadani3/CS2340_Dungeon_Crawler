package com.example.dungeongame.model.enemies;

/**
 * Defines the Locatable characteristic. Objects that are locatable have an X-coordinate and a
 * Y-coordinate that is retrievable
 * @author Ethan Nguyen-Tu
 * @version 1.0.0
 */
public interface Locatable {

    /**
     * Returns the x-coordinate of an object.
     * @return int of the object's x-coordinate
     */
    int getX();


    /**
     * Returns the y-coordinate of an object.
     * @return int of the object's y-coordinate
     */
    int getY();

}
