package com.example.dungeongame.model.tileset;

/**
 * Class that specifies the victory condition tile for the player. The chosen tile for the victory
 * condition is the "gold block" tile.
 * ---
 * tile ID is calculated with the formual ID = column + row * 16, where column and row are
 * determined by the 16x16 squares in dungeontiles.png
 *
 * @author Ethan Nguyen-Tu
 * @version 1.0.1
 */
public class EndGameTile extends MapTile {

    public EndGameTile(int x, int y, int id) {
        super(x, y, id);
    }

    public EndGameTile(int x, int y) {
        super(x, y, 40); // Gold Block Tile = 8 + 2 * 16 = 40
    }

}
