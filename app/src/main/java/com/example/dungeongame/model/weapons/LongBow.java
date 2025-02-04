package com.example.dungeongame.model.weapons;

/**
 * This class defines a Long Bow. A Long Bow is double the attack power and weight of a Basic Bow.
 *
 * @author Ethan Nguyen-Tu
 * @version 1.0.0
 */
public class LongBow extends Bow {

    public LongBow() {
        setName("Long Bow");
        setAttackPower(getAttackPower() * 2);
        setWeight(getWeight() * 2);
        setRange(getRange() * 2);
    }

} // FIN
