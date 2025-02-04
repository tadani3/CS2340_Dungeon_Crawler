package com.example.dungeongame.model.weapons;

/**
 * Interface that characterizes an object as being able to load something.
 *
 * @author Ethan Nguyen-Tu
 * @version 1.0.0
 */
public interface Loadable {

    /**
     * Increases amount of something an object has available.
     * @param amount int of the number of items to load to the object.
     * @return true if the load succeeds, else false
     */
    boolean load(int amount);
} // FIN

