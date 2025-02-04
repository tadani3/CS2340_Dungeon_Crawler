package com.example.dungeongame.model;

import com.example.dungeongame.model.enemies.EnemyFactory;
import com.example.dungeongame.model.enemies.EnemySpawner;
import com.example.dungeongame.model.enemies.GhostEnemyFactory;
import com.example.dungeongame.model.enemies.GoblinEnemyFactory;
import com.example.dungeongame.model.powerups.PowerUpItem;
import com.example.dungeongame.model.tileset.ExitTile;
import com.example.dungeongame.model.tileset.MapTile;
import com.example.dungeongame.model.tileset.StartTile;

/**
 * Temporary class for setting the any special tiles.
 */
public class MapConfiguration {

    private static MapTile tile;

    /**
     * Sets all special tiles.
     */
    public static void setAll() {
        setStartTile();
        setVictoryTiles();
        setExitTiles();
        setSpawners();
    }

    /**
     * Places the player's start position on the maps. There should be a max of one start position
     * per map.
     */
    public static void setStartTile() {
        StartTile start = new StartTile(16, 16);
        GameMap.getRedStone().setTile(start);
        start.setPlayer();
    }

    /**
     * Places the victory tile locations on the maps. The player should be sent to the end screen
     * when the player steps on this tile.
     */
    public static void setVictoryTiles() {
        // RED STONE MAP
        tile = GameMap.getRedStone().getTileAt(19, 11);
    }

    /**
     * Places the exit locations on the maps. The player should be sent to another map's exit
     * location when the player steps on one of these tiles.
     */
    public static void setExitTiles() {

        // SPOOKY HOUSE MAP
        // - go to red stone
        tile = GameMap.getSpookyHouse().getTileAt(3, 3);
        ((ExitTile) tile).setNextInfo(GameMap.getRedStone(), 3, 5); //red stone

        tile = GameMap.getSpookyHouse().getTileAt(4, 3);
        ((ExitTile) tile).setNextInfo(GameMap.getRedStone(), 3, 4); //red stone

        tile = GameMap.getSpookyHouse().getTileAt(5, 3);
        ((ExitTile) tile).setNextInfo(GameMap.getRedStone(), 3, 3); //red stone

        tile = GameMap.getSpookyHouse().getTileAt(46, 11);
        ((ExitTile) tile).setNextInfo(GameMap.getRedStone(), 9, 23);

        tile = GameMap.getSpookyHouse().getTileAt(46, 12);
        ((ExitTile) tile).setNextInfo(GameMap.getRedStone(), 9, 24);

        tile = GameMap.getSpookyHouse().getTileAt(46, 13);
        ((ExitTile) tile).setNextInfo(GameMap.getRedStone(), 9, 25);

        // RED STONE MAP (This is the starting room of the game)
        // got to spooky house
        tile = GameMap.getRedStone().getTileAt(2, 3);
        ((ExitTile) tile).setNextInfo(GameMap.getSpookyHouse(), 3, 4);

        tile = GameMap.getRedStone().getTileAt(2, 4);
        ((ExitTile) tile).setNextInfo(GameMap.getSpookyHouse(), 4, 4);

        tile = GameMap.getRedStone().getTileAt(2, 5);
        ((ExitTile) tile).setNextInfo(GameMap.getSpookyHouse(), 5, 4);

        tile = GameMap.getRedStone().getTileAt(8, 23);
        ((ExitTile) tile).setNextInfo(GameMap.getSpookyHouse(), 45, 11);

        tile = GameMap.getRedStone().getTileAt(8, 24);
        ((ExitTile) tile).setNextInfo(GameMap.getSpookyHouse(), 45, 12);

        tile = GameMap.getRedStone().getTileAt(8, 25);
        ((ExitTile) tile).setNextInfo(GameMap.getSpookyHouse(), 45, 13);
        // go to gray stone

        tile = GameMap.getRedStone().getTileAt(34, 3);
        ((ExitTile) tile).setNextInfo(GameMap.getGrayStone(), 4, 3);

        tile = GameMap.getRedStone().getTileAt(34, 4);
        ((ExitTile) tile).setNextInfo(GameMap.getGrayStone(), 4, 4);

        tile = GameMap.getRedStone().getTileAt(34, 5);
        ((ExitTile) tile).setNextInfo(GameMap.getGrayStone(), 4, 5);

        tile = GameMap.getRedStone().getTileAt(44, 14);
        ((ExitTile) tile).setNextInfo(GameMap.getGrayStone(), 4, 26);

        tile = GameMap.getRedStone().getTileAt(44, 15);
        ((ExitTile) tile).setNextInfo(GameMap.getGrayStone(), 4, 27);

        tile = GameMap.getRedStone().getTileAt(44, 16);
        ((ExitTile) tile).setNextInfo(GameMap.getGrayStone(), 4, 28);

        //GRAY STONE MAP
        //go to red stone
        tile = GameMap.getGrayStone().getTileAt(3, 3);
        ((ExitTile) tile).setNextInfo(GameMap.getRedStone(), 33, 3);

        tile = GameMap.getGrayStone().getTileAt(3, 4);
        ((ExitTile) tile).setNextInfo(GameMap.getRedStone(), 33, 4);

        tile = GameMap.getGrayStone().getTileAt(3, 5);
        ((ExitTile) tile).setNextInfo(GameMap.getRedStone(), 33, 5);

        tile = GameMap.getGrayStone().getTileAt(3, 26);
        ((ExitTile) tile).setNextInfo(GameMap.getRedStone(), 43, 14);

        tile = GameMap.getGrayStone().getTileAt(3, 27);
        ((ExitTile) tile).setNextInfo(GameMap.getRedStone(), 43, 15);

        tile = GameMap.getGrayStone().getTileAt(3, 28);
        ((ExitTile) tile).setNextInfo(GameMap.getRedStone(), 43, 16);


    }

    /**
     * Places the spawners on the maps. Spawners are used to spawn enemies at random intervals.
     */
    public static void setSpawners() {
        // Factories
        EnemyFactory phantomFactory = new GhostEnemyFactory("Phantom");
        EnemyFactory michelinFactory = new GhostEnemyFactory("Michelin");

        EnemyFactory ogreFactory = new GoblinEnemyFactory("Ogre");
        EnemyFactory orcFactory = new GoblinEnemyFactory("Orc");

        //spawn PowerUpItems
        PowerUpItem healthItem = new PowerUpItem("health",
                GameMap.getRedStone(), 10, 12);
        PowerUpItem coinItem = new PowerUpItem("money",
                GameMap.getRedStone(), 12, 12);
        PowerUpItem healthItem2 = new PowerUpItem("health",
                GameMap.getRedStone(), 35, 34);
        PowerUpItem healthItem3 = new PowerUpItem("health",
                GameMap.getGrayStone(), 31, 35);
        PowerUpItem attackItem = new PowerUpItem("attack",
                GameMap.getRedStone(), 10, 14);
        PowerUpItem teleportItem = new PowerUpItem("teleport",
                GameMap.getRedStone(), 10, 24);


        // SPOOKY HOUSE MAP
        GameMap.getSpookyHouse().addSpawner(new EnemySpawner(36, 4, phantomFactory,
                0.002f, GameMap.getSpookyHouse()));

        GameMap.getSpookyHouse().addSpawner(new EnemySpawner(40, 27, michelinFactory,
                0.002f, GameMap.getSpookyHouse()));

        // RED STONE MAP
        GameMap.getRedStone().addSpawner(new EnemySpawner(26, 29, ogreFactory,
                0.002f, GameMap.getRedStone()));

        GameMap.getRedStone().addSpawner(new EnemySpawner(28, 14, orcFactory,
                0.002f, GameMap.getRedStone()));

        for (EnemySpawner spawner: GameMap.getSpookyHouse().getSpawners()) {
            spawner.forceSpawn();
        }

        for (EnemySpawner spawner: GameMap.getRedStone().getSpawners()) {
            spawner.forceSpawn();
        }
    }
    
}
