package com.example.dungeongame.model.enemies;

import com.example.dungeongame.model.GameMap;

/**
 * Class that extends the EnemyFactory.java abstract class to crete enemies of type: Ghost.
 * ---
 * Pattern: Factory Design Pattern
 * @author Ethan Nguyen-Tu
 * @version 1.0.0
 */
public class GhostEnemyFactory extends EnemyFactory {
    private String subType;

    public GhostEnemyFactory(String subType) {
        setType("Ghost");
        this.subType = subType;
    }

    /**
     * Creates a Ghost class enemy.
     *
     * @param map GameMap that the enemy will be created on
     * @param x int of the x starting coordinate of the enemy
     * @param y int of the y starting coordinate of the enemy
     * @return Enemy of type Ghost.
     */
    @Override
    public Enemy createEnemy(GameMap map, int x, int y) {
        Object[] drops = {};
        Enemy newGhost;

        switch (this.subType) {
        case "Michelin":
            newGhost = new MichelinGhost(drops, map, x, y);
            if (newGhost.hasDied()) {
                return null;
            }
            newGhost.setMovementStrategy(new DirectMovement(newGhost));
            return newGhost;
        case "Phantom":
            newGhost = new PhantomGhost(drops, map, x, y);
            if (newGhost.hasDied()) {
                return null;
            }
            newGhost.setMovementStrategy(new DirectMovement(newGhost));
            return newGhost;
        default:
            return null;
        }
    }

} // FIN
