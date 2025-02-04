package com.example.dungeongame.model.weapons;

import com.example.dungeongame.model.enemies.Enemy;

/**
 * Class that details a basic spear.
 *
 * @author Ethan Nguyen-Tu
 * @version 1.0.0
 */
public class Spear extends Weapon {

    public Spear() {
        setName("Basic Spear");
        setAttackPower(75); // Currently set to insta-kill
        setDurability(30);
        setWeight(10);
        setRange(4);
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
