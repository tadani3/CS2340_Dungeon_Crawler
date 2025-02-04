package com.example.dungeongame.model.enemies;

import com.example.dungeongame.model.Clock;
import com.example.dungeongame.model.GameMap;
import com.example.dungeongame.model.Pauseable;
import com.example.dungeongame.model.Player;
import com.example.dungeongame.model.Tickable;
import com.example.dungeongame.model.Util;

import java.util.logging.Logger;

/**
 * EnemySpawner is a factory class for creating enemies at random intervals.
 */
public class EnemySpawner implements Locatable, Pauseable, Tickable {
    private static final Clock CLOCK = Clock.getClock();
    private static final Player PLAYER = Player.getPlayer();
    private int x;
    private int y;
    private EnemyFactory enemyFactory;
    private double spawnChance;
    private int spawnFreq = 20; // How long between spawns-
    private GameMap map;
    private int ticksSinceLastSpawn = 0;
    private boolean on = true;

    /**
     * Constructor for EnemySpawner.
     * @param x The map X coordinate to spawn the enemy at.
     * @param y The map Y coordinate to spawn the enemy at.
     * @param enemyFactory The factory for the enemy it should create.
     * @param spawnChance The rate at which the enemy spawns, in spawns per tick.
     * @param map The map to spawn the enemy on.
     */
    public EnemySpawner(int x, int y, EnemyFactory enemyFactory, double spawnChance, GameMap map) {
        CLOCK.add(this);
        this.x = x;
        this.y = y;
        this.enemyFactory = enemyFactory;
        this.spawnChance = spawnChance;
        this.map = map;
    }


    // LOCATABLE
    public int getX() {
        return this.x;
    }


    public int getY() {
        return this.y;
    }


    // PAUSEABLE
    public void pause() {
        this.on = false;
        Logger.getGlobal().info("Enemy Spawner " + this + " Paused");
    }


    public void start() {
        this.on = true;
        Logger.getGlobal().info("Enemy Spawner " + this + " Started");
    }


    /**
     * Forces a spawn event of the enemy type.
     */
    public void forceSpawn() {
        Logger.getGlobal().info("Enemy was spawned.");
        this.ticksSinceLastSpawn = 0;
        enemyFactory.createEnemy(this.map, this.x, this.y);
    }


    // TICKABLE
    /**
     * Method that is called every tick of the game. See the Tickable interface for more details.
     */
    public void onTick() {
        this.ticksSinceLastSpawn++;
        if (on) {
            if (this.ticksSinceLastSpawn < spawnFreq
                    || this.map != GameMap.getActiveMap() // don't spawn if the map is not active
                    || !Util.inRange(PLAYER, this, 10) // Spawner <10 tiles from Player
                    || Math.random() > spawnChance) { // Random Spawn
                return;
            }

            this.forceSpawn(); // an enemy should be spawned
        }
    }

} // FIN
