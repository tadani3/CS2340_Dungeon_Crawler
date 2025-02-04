package com.example.dungeongame.viewmodels;

import com.example.dungeongame.model.tileset.MapTile;

/**
 * This class contains the graphics information for rendering a tile on the map. This should not be
 * utilized for normal game logic. MapTile should be used instead.
 */
public class GraphicTile {
    private MapTile tile;
    private int x;
    private int y;

    public GraphicTile(MapTile tile, int x, int y) {
        this.tile = tile;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return this.tile.getId();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
