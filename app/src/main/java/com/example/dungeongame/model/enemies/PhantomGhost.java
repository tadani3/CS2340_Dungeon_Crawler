package com.example.dungeongame.model.enemies;

import com.example.dungeongame.model.GameMap;

/**
 * Class that details the Spooky Enemy type.
 * ---
 * Pattern: Factory Design Pattern
 * @author Ethan Nguyen-Tu
 * @version 1.0.0
 */
public class PhantomGhost extends Enemy {
    // DEFAULT VALUES
    public static final int DEFAULT_HEALTH = 100;
    public static final int DEFAULT_DAMAGE = 8;

    // CONSTRUCTORS
    public PhantomGhost(int maxHealth, int basicDamage, Object[] drops, GameMap map, int x,
                        int y) {
        super("PhantomGhost", maxHealth, basicDamage, drops, map, x, y);
        setCount(getCount() + 1);
    }

    public PhantomGhost(int basicDamage, Object[] drops, GameMap map, int x, int y) {
        this(DEFAULT_HEALTH, basicDamage, drops, map, x, y);
    }

    public PhantomGhost(Object[] drops, GameMap map, int x, int y) {
        this(DEFAULT_DAMAGE, drops, map, x, y);
    }

    @Override
    public int getScore() {
        return 16;
    }

} // FIN
