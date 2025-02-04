package com.example.dungeongame.viewmodels.strategies;

import com.example.dungeongame.model.Player;
import com.example.dungeongame.model.tileset.MapTile;
import com.example.dungeongame.model.tileset.WallTile;
import com.example.dungeongame.viewmodels.GameViewModel;

/**
 * Normal walking, where each step is one grid tile.
 */
public class Walking implements MovementStrategy {
    public void moveLeft() {
        Player player = Player.getPlayer();
        MapTile nextTile = GameViewModel.getGameViewModel().getMap().getTileAt(player.getX() - 1,
                player.getY());
        if (nextTile != null && nextTile.getClass() != WallTile.class) {
            player.setX(player.getX() - 1);
        }
    }

    public void moveRight() {
        Player player = Player.getPlayer();
        MapTile nextTile = GameViewModel.getGameViewModel().getMap().getTileAt(player.getX() + 1,
                player.getY());
        if (nextTile != null && nextTile.getClass() != WallTile.class) {
            player.setX(player.getX() + 1);
        }
    }

    public void moveUp() {
        Player player = Player.getPlayer();
        MapTile nextTile = GameViewModel.getGameViewModel().getMap().getTileAt(player.getX(),
                player.getY() - 1);
        if (nextTile != null && nextTile.getClass() != WallTile.class) {
            player.setY(player.getY() - 1);
        }
    }

    public void moveDown() {
        Player player = Player.getPlayer();
        MapTile nextTile = GameViewModel.getGameViewModel().getMap().getTileAt(player.getX(),
                player.getY() + 1);
        if (nextTile != null && nextTile.getClass() != WallTile.class) {
            player.setY(player.getY() + 1);
        }
    }
}
