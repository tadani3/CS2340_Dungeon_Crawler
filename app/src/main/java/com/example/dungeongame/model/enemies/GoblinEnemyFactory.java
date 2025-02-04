package com.example.dungeongame.model.enemies;

import com.example.dungeongame.model.GameMap;

/**
 * Class that extends the EnemyFactory.java abstract class to crete enemies of type: Goblin.
 * ---
 * Pattern: Factory Design Pattern
 * @author Ethan Nguyen-Tu
 * @version 1.0.0
 */
public class GoblinEnemyFactory extends EnemyFactory {

    private String subType;
    public GoblinEnemyFactory(String subType) {
        setType("Goblin");
        this.subType = subType;
    }

    /**
     * Creates a Goblin class enemy.
     *
     * @param map GameMap that the enemy will be created on
     * @param x int of the x starting coordinate of the enemy
     * @param y int of the y starting coordinate of the enemy
     * @return Enemy of type Goblin.
     */
    @Override
    public Enemy createEnemy(GameMap map, int x, int y) {
        Object[] drops = {};
        Enemy newGoblin;

        switch (this.subType) {
        case "Ogre":
            newGoblin = new OgreGoblin(drops, map, x, y);
            if (newGoblin.hasDied()) {
                return null;
            }
            newGoblin.setMovementStrategy(new ObstructionAwareMovement(newGoblin));
            return newGoblin;
        case "Orc":
            newGoblin = new OrcGoblin(drops, map, x, y);
            if (newGoblin.hasDied()) {
                return null;
            }
            newGoblin.setMovementStrategy(new ObstructionAwareMovement(newGoblin));
            return newGoblin;
        default:
            return null;
        }
    }

} // FIN
