package com.example.dungeongame.model.enemies;

import com.example.dungeongame.model.GameMap;

/**
 * Class that details the OgreGoblin Enemy type.
 * ---
 * Pattern: Factory Design Pattern
 * @author Ethan Nguyen-Tu
 * @version 1.0.0
 */
public class OgreGoblin extends Enemy {
    // DEFAULT VALUES
    public static final int DEFAULT_HEALTH = 100;
    public static final int DEFAULT_DAMAGE = 6;

    // CONSTRUCTORS
    public OgreGoblin(int maxHealth, int basicDamage, Object[] drops, GameMap map, int x,
                      int y) {
        super("OgreGoblin", maxHealth, basicDamage, drops, map, x, y);
        setCount(getCount() + 1);
    }

    public OgreGoblin(int basicDamage, Object[] drops, GameMap map, int x, int y) {
        this(DEFAULT_HEALTH, basicDamage, drops, map, x, y);
    }

    public OgreGoblin(Object[] drops, GameMap map, int x, int y) {
        this(DEFAULT_DAMAGE, drops, map, x, y);
    }

    @Override
    public int getScore() {
        return 12;
    }

} // FIN
