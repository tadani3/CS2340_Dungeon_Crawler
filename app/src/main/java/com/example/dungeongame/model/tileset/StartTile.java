package com.example.dungeongame.model.tileset;

import com.example.dungeongame.model.Player;
import com.example.dungeongame.model.weapons.Sword;

/**
 * Class that specifies the start position tile for the player. This tile can be any tile, but
 * the default is whatever tile is in the top left corner.
 * ---
 * tile ID is calculated with the formual ID = column + row * 16, where column and row are
 * determined by the 16x16 squares in dungeontiles.png
 *
 * @author Ethan Nguyen-Tu
 * @version 1.0.1
 */
public class StartTile extends MapTile {

    public StartTile(int x, int y, int id) {
        super(x, y, id);
    }

    public StartTile(int x, int y) {
        super(x, y, 0); // Any tile.
    }

    public void setPlayer() { // Player's initial setup.
        Player p = Player.getPlayer();
        p.setRightHand(new Sword());
        p.setX(this.getX());
        p.setY(this.getY());
        p.reset();
    }
}
