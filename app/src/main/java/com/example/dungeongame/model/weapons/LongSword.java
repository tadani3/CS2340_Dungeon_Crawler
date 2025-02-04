package com.example.dungeongame.model.weapons;

/**
 * This class defines a Long Sword. A Long Sword has half the attack power but double the weight and
 * range of a Basic Sword.
 *
 * @author Ethan Nguyen-Tu
 * @version 1.0.0
 */
public class LongSword extends Sword {

    public LongSword() {
        setName("Long Sword");
        setAttackPower(getAttackPower() / 2);
        setWeight(getWeight() * 2);
        setRange(getRange() * 2);
    }

} // FIN
