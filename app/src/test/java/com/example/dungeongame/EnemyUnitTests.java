package com.example.dungeongame;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.dungeongame.model.Clock;
import com.example.dungeongame.model.enemies.EnemySpawner;
import com.example.dungeongame.model.GameMap;
import com.example.dungeongame.model.Player;
import com.example.dungeongame.model.enemies.DirectMovement;
import com.example.dungeongame.model.enemies.Enemy;
import com.example.dungeongame.model.enemies.EnemyFactory;
import com.example.dungeongame.model.enemies.GoblinEnemyFactory;
import com.example.dungeongame.model.enemies.OrcGoblin;
import com.example.dungeongame.model.tileset.MapTile;

public class EnemyUnitTests {
    GameMap map;
    @Before
    public void setUp() {
        MapTile[][] tiles = new MapTile[10][10];
        tiles[0][0] = new MapTile(0, 0, 0);
        tiles[0][1] = new MapTile(1, 0, 1);
        tiles[0][2] = new MapTile(2, 0, 3);
        tiles[1][0] = new MapTile(0, 1, 4);
        map = new GameMap(tiles);
    }

    @Test
    public void testEnemyCreation() {
        // Checking to see if the newly-created enemy gets added to the map
        assertEquals(0, map.getEnemies().size());

        // Creating a new enemy
        Object[] drops = new Object[0];
        Enemy e = new OrcGoblin(drops, map, 23, 40);

        // Checking to see if the enemy was added to the map
        assertEquals(1, map.getEnemies().size());
        assertEquals(e, map.getEnemies().get(0));
        assertFalse(e.hasDied());

        // Checking to see if the enemy was added to the map at the correct location
        assertEquals(23, e.getX());
        assertEquals(40, e.getY());
    }

    @Test
    public void testEnemyKilling() {
        assertEquals(0, map.getEnemies().size());

        Object[] drops = new Object[0];
        Enemy e = new OrcGoblin(drops, map, 23, 40);

        // Checking to see if the enemy was added to the map
        assertEquals(1, map.getEnemies().size());

        // Killing the enemy by invoking its kill method
        e.kill();

        // Checking to see if the enemy was removed from the map
        assertEquals(0, map.getEnemies().size());

        // Checking to see if the enemy's death was registered
        assertTrue(e.hasDied());
    }

    @Test
    public void testDirectMovement() {
        // Simulating a player
        Player p = Player.getPlayer();
        p.setX(10);
        p.setY(0);

        // Creating a new enemy
        GameMap.setActiveMap(map);
        Object[] drops = new Object[0];
        Enemy e = new OrcGoblin(drops, map, 0, 0);
        e.setMovementStrategy(new DirectMovement(e, 1));

        e.getMovementStrategy().onTick();

        // should not have moved yet
        assertEquals(0, e.getX());
        assertEquals(0, e.getY());

        e.getMovementStrategy().onTick();

        // should have moved
        assertEquals(1, e.getX());
        assertEquals(0, e.getY());
    }

    @Test
    public void testNoMovementWhenInactiveMap() {
        // Simulating a player
        Player p = Player.getPlayer();
        p.setX(10);
        p.setY(0);

        // Creating a new enemy
        GameMap.setActiveMap(null); // just anything other than the current map
        Object[] drops = new Object[0];
        Enemy e = new OrcGoblin(drops, map, 0, 0);
        e.setMovementStrategy(new DirectMovement(e, 1));

        e.getMovementStrategy().onTick();

        // should not have moved yet
        assertEquals(0, e.getX());
        assertEquals(0, e.getY());

        e.getMovementStrategy().onTick();

        // should have moved
        assertEquals(0, e.getX());
        assertEquals(0, e.getY());
    }

    @Test
    public void testSpawning() {
        // Since the player must be in range of the spawner, will simulate a player
        Player p = Player.getPlayer();
        p.setX(0);
        p.setY(0);

        // Creating a new spawner
        GameMap.setActiveMap(map);
        Object[] drops = new Object[0];
        EnemyFactory enemyFactory = new GoblinEnemyFactory("Orc");
        EnemySpawner spawner = new EnemySpawner(0, 0, enemyFactory, 1, map);
        map.addSpawner(spawner);

        // Checking to see if the spawner was added to the map
        assertEquals(1, map.getSpawners().size());
        assertEquals(spawner, map.getSpawners().get(0));

        // Checking to see if the spawner spawns an enemy
        assertEquals(0, map.getEnemies().size());

        for (int i = 0; i < 21; i++) {
            spawner.onTick(); // needs at least 20 ticks in between spawns
        }

        // Checking to see if the enemy was added to the map
        assertEquals(1, map.getEnemies().size());
        assertEquals(0, map.getEnemies().get(0).getX());
        assertEquals(0, map.getEnemies().get(0).getY());
    }

    @Test
    public void testNoSpawningOnInactiveMap() {
        // Since the player must be in range of the spawner, will simulate a player
        Player p = Player.getPlayer();
        p.setX(0);
        p.setY(0);

        // Creating a new spawner
        GameMap.setActiveMap(null); // just anything other than the current map
        Object[] drops = new Object[0];
        EnemyFactory enemyFactory = new GoblinEnemyFactory("Orc");
        EnemySpawner spawner = new EnemySpawner(0, 0, enemyFactory, 1, map);
        map.addSpawner(spawner);

        // Checking to see if the spawner was added to the map
        assertEquals(1, map.getSpawners().size());
        assertEquals(spawner, map.getSpawners().get(0));

        // Checking to see if the spawner spawns an enemy
        assertEquals(0, map.getEnemies().size());

        for (int i = 0; i < 21; i++) {
            spawner.onTick(); // needs at least 20 ticks in between spawns
        }

        // Checking to see if the enemy was added to the map
        assertEquals(0, map.getEnemies().size());
    }

    /**
     * Test to evaluate if resources are properly released following the death of an enemy.
     * Daniel Cooper
     */
    @Test
    public void testIfKillRemovesEnemy() {
        Player p = Player.getPlayer();
        p.setX(0);
        p.setY(0);

        Clock.getClock().clear();

        // Creating a new spawner
        GameMap.setActiveMap(map);
        EnemyFactory enemyFactory = new GoblinEnemyFactory("Orc");
        EnemySpawner spawner = new EnemySpawner(0, 0, enemyFactory, 1, map);
        map.addSpawner(spawner);

        // Forcing a spawn
        spawner.forceSpawn();

        // Checking to see if the enemy was added to the map
        assertEquals(1, map.getEnemies().size());

        // Checking that enemy was added to tickables (2 because spawner and enemy)
        assertEquals(2, Clock.getClock().getTickables().size());

        // Killing the enemy
        map.getEnemies().get(0).kill();

        // Checking to see if the enemy was removed from the map and tickables
        assertEquals(0, map.getEnemies().size());
        assertEquals(1, Clock.getClock().getTickables().size());
    }
}
