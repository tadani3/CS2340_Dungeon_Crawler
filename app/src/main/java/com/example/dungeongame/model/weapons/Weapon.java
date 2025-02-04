package com.example.dungeongame.model.weapons;

import com.example.dungeongame.model.Util;
import com.example.dungeongame.model.enemies.Locatable;

/**
 * Abstract class that details a Weapon object.
 * @author Ethan Nguyen-Tu
 * @version 1.1.0
 */
public abstract class Weapon {

    private String name;
    private int attackPower; // How much damage the weapon deals to other objects
    private int durability;
    private int weight;
    private double range; // How far the weapon is able to attack


    /**
     * Decreases the given object's health by the attack power of the weapon and decreases the
     * weapon's durability.
     * @param o Object that should be attacked by the weapon
     * @return Boolean true if the attack succeeds and false if the attack fails
     */
    public abstract boolean attack(Object o);


    /**
     * Repairs the weapon, increasing its durability by the given amount.
     * @param repairAmount Int amount that the weapon's durability should be increased by
     * @return Boolean true if the repair succeeds and false if the repair fails
     */
    public boolean repair(int repairAmount) {
        this.durability += repairAmount;
        return true;
    }


    /**
     * Decreases the weapon, increasing its durability by the specified amount.
     * @param amount Int amount that the weapon's durability should be decreased by
     */
    public void decreaseDurability(int amount) {
        this.durability -= amount;
    }


    /**
     * Method that returns true if the target is within range of the origin.
     * @param origin Locatable origin of the inRange check
     * @param target Locatable target of the inRange check
     * @return boolean true if the target is within the origin's range, else false
     */
    public boolean inRange(Locatable origin, Locatable target) {
        return Util.manhattanDistance(origin.getX(), origin.getY(), target.getX(), target.getY())
                < this.range;
    }


    // GETTERS AND SETTERS
    // Name
    /**
     * Getter for the name of the weapon.
     * @return String of the name of the weapon
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the weapon.
     * @param name String of the name of the weapon
     */
    public void setName(String name) {
        this.name = name;
    }


    // Attack Power
    /**
     * Getter for the attack power of the weapon.
     * @return Int of the attack power of the weapon
     */
    public int getAttackPower() {
        return attackPower;
    }

    /**
     * Setter for the attack power of the weapon.
     * @param attackPower Int attack power that the weapon should be equal to
     */
    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }


    // Durability
    /**
     * Getter for the durability of the weapon.
     * @return Int of the durability of the weapon
     */
    public int getDurability() {
        return durability;
    }

    /**
     * Setter for the durability of the weapon.
     * @param durability Int durability that the weapon should be equal to
     */
    public void setDurability(int durability) {
        this.durability = durability;
    }


    // Weight
    /**
     * Getter for the weight of the weapon.
     * @return Int of the weight of the weapon
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Setter for the weight of the weapon.
     * @param weight Int weight that the weapon should be equal to
     */
    public void setWeight(int weight) {
        this.weight = weight;
    }


    // Range
    /**
     * Getter for the range of the weapon.
     * @return double of the weapon's range
     */
    public double getRange() {
        return range;
    }

    /**
     * Setter for the range of the weapon.
     * @param range double that the weapon's range should be
     */
    public void setRange(double range) {
        this.range = range;
    }

} // FIN
