package com.example.dungeongame.model.tileset;

/**
 * Class that represents the model of a generic map tile.
 */
public class MapTile {
    private int id; //Id number
    private int x; //Map x coordinate of the tile
    private int y; //Map Y coordinate of the tile
    private boolean traversable; //Flag to determine whether a tile is an obstacle

    public MapTile(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String toString() {
        return "" + id;
    }

}
