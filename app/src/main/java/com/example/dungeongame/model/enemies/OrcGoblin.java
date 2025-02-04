package com.example.dungeongame.model.enemies;

import com.example.dungeongame.model.GameMap;

/**
 * Class that details the OrcGoblin Enemy type.
 * ---
 * Pattern: Factory Design Pattern
 * @author Ethan Nguyen-Tu
 * @version 1.0.0
 */
public class OrcGoblin extends Enemy {
    // DEFAULT VALUES
    public static final int DEFAULT_HEALTH = 75;
    public static final int DEFAULT_DAMAGE = 4;

    // CONSTRUCTORS
    public OrcGoblin(int maxHealth, int basicDamage, Object[] drops, GameMap map, int x,
                     int y) {
        super("OrcGoblin", maxHealth, basicDamage, drops, map, x, y);
        setCount(getCount() + 1);
    }

    public OrcGoblin(int basicDamage, Object[] drops, GameMap map, int x, int y) {
        this(DEFAULT_HEALTH, basicDamage, drops, map, x, y);
    }

    public OrcGoblin(Object[] drops, GameMap map, int x, int y) {
        this(DEFAULT_DAMAGE, drops, map, x, y);
    }

    @Override
    public int getScore() {
        return 8;
    }
} // FIN
