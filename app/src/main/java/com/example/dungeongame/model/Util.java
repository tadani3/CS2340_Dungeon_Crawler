package com.example.dungeongame.model;

import com.example.dungeongame.model.enemies.Locatable;

/**
 * Class that details calculation utilities for the game.
 * @author Ethan Nguyen-Tu
 * @version 1.0.0
 */
public class Util {

    /**
     * Returns the manhattan distance of two x,y-coordinates.
     *
     * @param x1 x-coordinate of the first position
     * @param y1 y-coordinate of the first position
     * @param x2 x-coordinate of the second position
     * @param y2 y-coordinate of the second position
     * @return int of the manhattan distance of the two positions
     */
    public static int manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }


    /**
     * Method that returns true if the target is within range of the origin.
     *
     * @param origin Locatable origin of the inRange check
     * @param target Locatable target of the inRange check
     * @param range double distance the target should be from the origin
     * @return boolean true if the target is within the origin's range, else false
     */
    public static boolean inRange(Locatable origin, Locatable target, double range) {
        return Util.manhattanDistance(origin.getX(), origin.getY(), target.getX(), target.getY())
                < range;
    }

} // FIN
