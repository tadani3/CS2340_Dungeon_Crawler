package com.example.dungeongame.model;
import android.content.res.XmlResourceParser;

import androidx.annotation.VisibleForTesting;

import com.example.dungeongame.model.enemies.Enemy;
import com.example.dungeongame.model.powerups.PowerUpItem;
import com.example.dungeongame.model.enemies.EnemySpawner;
import com.example.dungeongame.model.tileset.EndGameTile;
import com.example.dungeongame.model.tileset.ExitTile;
import com.example.dungeongame.model.tileset.MapTile;
import com.example.dungeongame.model.tileset.StartTile;
import com.example.dungeongame.model.tileset.WallTile;

import java.util.ArrayList;
import java.util.Stack;

public class GameMap implements Pauseable {
    private int width;
    private int height;
    private MapTile[][] tiles;

    private final ArrayList<PowerUpItem> powerUpItems;
    private final ArrayList<EnemySpawner> spawners;
    private final ArrayList<Enemy> enemies;

    private static GameMap redStone;
    private static GameMap spookyHouse;
    private static GameMap grayStone;
    private static GameMap activeMap = null;

    /**
     * Constructor for unit tests ONLY!
     * @param tiles an array of [y][x] MapTile objects
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public GameMap(MapTile[][] tiles) {
        this.width = tiles[0].length;
        this.height = tiles.length;
        this.tiles = tiles;
        this.spawners = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.powerUpItems = new ArrayList<>();
    }

    /**
     * Constructor for GameMap objects. Parses the given XML file to create a GameMap object.
     * XML file should come from an activity with a valid context to load from the res folder.
     * @param map an XmlResourceParser object that contains the map data
     */
    public GameMap(XmlResourceParser map) {
        Stack<String> path = new Stack<String>();
        String rawMapData = null;
        this.spawners = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.powerUpItems = new ArrayList<>();


        while (true) {
            String name = map.getText();
            boolean entered = false;

            try {
                if (map.getEventType() == XmlResourceParser.START_TAG) {
                    String s = map.getName();
                    path.push(s);
                    entered = true;
                } else if (map.getEventType() == XmlResourceParser.END_TAG) {
                    path.pop();
                } else if (map.getEventType() == XmlResourceParser.END_DOCUMENT) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            if (path.size() == 4 && path.get(0).equals("map") && path.get(1).equals("root")
                    && path.get(2).equals("layers") && path.get(3).equals("layer") && entered) {
                // We are in a layer tag, now to see if it is the right one
                int attribCount = map.getAttributeCount();
                String layerName = null;
                String tileset = null;

                if (attribCount >= 1) {
                    layerName = map.getAttributeValue(0);
                }
                if (attribCount >= 2) {
                    tileset = map.getAttributeValue(1);
                }

                if (layerName != null && tileset != null
                        && layerName.equals("world") && tileset.equals("dungeontiles.png")) {
                    // We are in the right layer, now to get the data
                    try {
                        map.next();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    rawMapData = map.getText();
                    break;
                }
            }

            try {
                map.next();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

        if (rawMapData == null) {
            return;
        }

        // breaking the raw map data into lines
        String[] lines = rawMapData.split("\n");
        this.height = lines.length;

        // the number of elements in the first line is the width
        // elements are separated by commas
        for (int i = 0; i < lines.length; i++) {
            String[] elements = lines[i].split(",");
            if (i == 0) {
                this.width = elements.length;
                this.tiles = new MapTile[this.height][this.width];
            }
            int amtSkipped = 0;
            for (int j = 0; j < elements.length; j++) {
                double d = 0;
                try {
                    d = Double.parseDouble(elements[j]);
                } catch (Exception e) {
                    amtSkipped++;
                    continue;
                }

                // the digits to the left of the decimal point are the x tile coord, and the digits
                // to the right of the decimal point are the y tile coord
                int tileMapX = (int) d;

                int tileMapY = 0;
                if (elements[j].contains(".")) {
                    // parse the y coord
                    String[] yStr = elements[j].split("\\.");
                    tileMapY = Integer.parseInt(yStr[1]);
                }

                // the tileset is 16 tiles wide, so *16 is for jumping a row for each y
                if (tileMapX < 0) {
                    continue; // don't add air tiles
                }
                int id = tileMapX + tileMapY * 16;
                this.tiles[i][j - amtSkipped] = createTile(j, i, id);
            }
        }

        map.close();
    }

    /**
     * Method that returns the width of the map
     * @return int width of the map
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Method that returns the height of the map
     * @return int height of the map
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Method that returns the tile at the given coordinates
     * @param x x coordinate of the tile, in tile space
     * @param y y coordinate of the tile, in tile space
     * @return MapTile object at the given coordinates
     */
    public MapTile getTileAt(int x, int y) {
        // checking bounds
        if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
            return null;
        }

        return this.tiles[y][x];
    }

    /**
     * A decoder function that creates a special tile based on the given id
     * @param x x coordinate of the tile, in tile space
     * @param y y coordinate of the tile, in tile space
     * @param id the id of the tile
     * @return MapTile object at the given coordinates, or a descendant of MapTile
     */
    private MapTile createTile(int x, int y, int id) {
        switch (id) {
        case 0:
            return new StartTile(x, y, id);
        case 23: // Column 8, Row 2 = Gold Block
            return new EndGameTile(x, y, id);
        case 85:
            return new ExitTile(x, y, id);
        case 4:
        case 54:
        case 211:
        case 212:
        case 213:
            return new WallTile(x, y, id);
        default:
            return new MapTile(x, y, id);
        }
    }

    /**
     * Replaces a MapTile with a given MapTile and returns the old MapTile.
     *
     * @param mapTile MapTile to replace the old MapTile
     * @return MapTile of the MapTile that was replaced
     */
    public MapTile setTile(MapTile mapTile) {
        MapTile old = this.tiles[mapTile.getX()][mapTile.getY()];
        this.tiles[mapTile.getX()][mapTile.getY()] = mapTile;

        return old;
    }

    /**
     * Adds an EnemySpawner to the map.
     * @param spawner the EnemySpawner to add
     */
    public void addSpawner(EnemySpawner spawner) {
        this.spawners.add(spawner);
    }

    /**
     * Returns all spawners associated with the map
     * @return ArrayList of EnemySpawner objects
     */
    public ArrayList<EnemySpawner> getSpawners() {
        return this.spawners;
    }

    /**
     * Adds an enemy to the map. Enemies cannot change maps, so they are bound by map
     * @param enemy the enemy to add
     */
    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }

    /**
     * Removes an enemy from the map.
     * @param enemy the enemy to remove
     */
    public void removeEnemy(Enemy enemy) {
        this.enemies.remove(enemy);
    }

    public ArrayList<Enemy> getEnemies() {
        return this.enemies;
    }

    public void clearEnemies() {
        ArrayList<Enemy> toRemove = new ArrayList<>(this.enemies);

        for (Enemy enemy : toRemove) {
            enemy.kill();
        }
    }


    /**
     * Adds a powerUpItem to the map. Items cannot change maps, so they are bound by map
     * @param item the powerUpItem to add
     */
    public void addPowerUpItem(PowerUpItem item) {
        this.powerUpItems.add(item);
    }

    /**
     * Removes a PowerUpItem from the map.
     * @param item the item to remove
     */
    public void removePowerUpItem(PowerUpItem item) {
        this.powerUpItems.remove(item);
    }
    public ArrayList<PowerUpItem> getPowerUpItems() {
        return this.powerUpItems;
    }

    public void clearPowerUps() {
        ArrayList<PowerUpItem> remove = new ArrayList<>(this.powerUpItems);
        for (PowerUpItem powerUp: remove) {
            powerUp.removeItem();
        }
    }



    public static GameMap getRedStone() {
        return redStone;
    }

    public static void setRedStone(GameMap map) {
        redStone = map;
    }

    public static GameMap getSpookyHouse() {
        return spookyHouse;
    }

    public static void setSpookyHouse(GameMap map) {
        spookyHouse = map;
    }
    public static GameMap getGrayStone() {
        return grayStone;
    }
    public static void setGrayStone(GameMap map) {
        grayStone = map;
    }

    /**
     * Sets the active map to the given map.
     * An active map is the one which the game is currently displaying.
     * @param map the map to set as active
     */
    public static void setActiveMap(GameMap map) {
        activeMap = map;
    }

    /**
     * Returns the active map.
     * @return the active map
     */
    public static GameMap getActiveMap() {
        return activeMap;
    }

    /**
     * Method that prints a grid visualization of a given GameMap to console.
     *
     * @return boolean true if print succeeds else false and prints exception to console
     */
    public boolean gridPrint() {
        try {
            String row = "";
            for (int x = 0; x < width - 1; x++) {
                for (int y = 0; y < height - 1; y++) {
                    System.out.println(tiles[y][x]);
                    row += getTileAt(x, y);
                }
                row += "\n";
            }
            System.out.println(row);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    // PAUSEABLE
    public void pause() {
        pauseSpawners();
    }

    public void start() {
        startSpawners();
    }

    public void pauseSpawners() {
        for (EnemySpawner s : this.spawners) {
            s.pause();
        }
    }

    public void startSpawners() {
        for (EnemySpawner s : this.spawners) {
            s.start();
        }
    }


} // FIN
