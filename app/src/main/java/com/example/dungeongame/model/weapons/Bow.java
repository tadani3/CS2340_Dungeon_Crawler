package com.example.dungeongame.model.weapons;

import com.example.dungeongame.model.Util;
import com.example.dungeongame.model.enemies.Enemy;
import com.example.dungeongame.model.enemies.Locatable;

/**
 * Class that details a basic bow.
 *
 * @author Ethan Nguyen-Tu
 * @version 1.0.0
 */
public class Bow extends Weapon implements Loadable, Locatable {

    private int x;
    private int y;

    private int quiver; // How many arrows the bow has
    private boolean hasQuiver = false;
    private boolean prepared;


    public Bow() {
        setName("Basic Bow");
        setAttackPower(50);
        setDurability(50);
        setWeight(5);
        setRange(6);
    }


    /**
     * Attack method of a bow. A bow deals less damage the further away the target is. To use a bow,
     * the bow must first be prepared(). The bow becomes no longer prepared once it has been fired.
     *
     * @param o Object that should be attacked by the weapon
     * @return True if the attack succeeds, else False
     */
    @Override
    public boolean attack(Object o) {
        if (!(o instanceof Enemy) || getDurability() < 1 || !prepared // Check if ability to fire
                || !hasQuiver || quiver < 1) { // Check if the bow has a quiver with arrows
            return false;
        }
        Enemy e = (Enemy) o;

        double damageDropOff = Util.manhattanDistance(x, y, e.getX(), e.getY()) / getRange();
        int damage = (int) (getAttackPower() / damageDropOff);

        e.setCurrentHealth(e.getCurrentHealth() - damage);
        decreaseDurability(1);

        prepared = false;
        quiver--;
        return true;
    }


    /**
     * Prepares the use of a bow by defining a position to fire from.
     * @param l Starting position of the bow.
     */
    public void prepare(Locatable l) {
        x = l.getX();
        y = l.getY();
        prepared = true;
    }


    /**
     * Increases amount of arrows the bow has available to fire.
     * @param numArrows int of the number of arrows to add to the bow.
     * @return boolean true if the load succeeds, else false
     */
    public boolean load(int numArrows) {
        quiver += numArrows;
        return true;
    }


    // GETTERS & SETTERS
    // Quiver
    /**
     * Getter for if the bow has an associated quiver.
     * @return boolean true if the bow has an associated quiver, else false
     */
    public boolean hasQuiver() {
        return hasQuiver;
    }

    /**
     * Setter for if the bow has an associated quiver.
     * @param hasQuiver boolean of if the bow has an associated quiver
     */
    public void setHasQuiver(boolean hasQuiver) {
        this.hasQuiver = hasQuiver;
    }


    // LOCATABLE
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


} // FIN
