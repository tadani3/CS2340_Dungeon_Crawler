package com.example.dungeongame.model.weapons;

import com.example.dungeongame.model.enemies.Enemy;

/**
 * Class that details a basic sword.
 *
 * @author Ethan Nguyen-Tu
 * @version 1.1.0
 */
public class Sword extends Weapon implements Repairable {

    public Sword() {
        setName("Basic Sword");
        setAttackPower(1000); // Currently set to insta-kill
        setDurability(20);
        setWeight(5);
        setRange(2);
    }

    @Override
    public boolean attack(Object o) {
        if (!(o instanceof Enemy) || getDurability() < 1) {
            return false;
        }
        Enemy e = (Enemy) o;
        e.setCurrentHealth(e.getCurrentHealth() - getAttackPower());
        decreaseDurability(1);
        return true;
    }


    @Override
    public boolean repair(int amount) {
        setDurability(getDurability() + amount);
        return true;
    }


} // FIN
