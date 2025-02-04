package com.example.dungeongame.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.dungeongame.model.Configuration;
import com.example.dungeongame.model.Player;
import com.example.dungeongame.model.tileset.EndGameTile;
import com.example.dungeongame.model.tileset.ExitTile;
import com.example.dungeongame.model.tileset.MapTile;

import java.util.logging.Logger;


/**
 * This class mainly handles player movement.
 */
public class PlayerViewModel extends ViewModel {
    private static PlayerViewModel instance;
    private PlayerViewModel() { }

    public static PlayerViewModel getPlayerViewModel() {
        if (instance == null) {
            instance = new PlayerViewModel();
        }
        return instance;
    }

    public void evaluateTile() {
        Player player = Player.getPlayer();
        MapTile tile = GameViewModel.getGameViewModel().getMap().getTileAt(player.getX(),
                player.getY());
        if (tile.getClass().equals(ExitTile.class)) {
            GameViewModel.getGameViewModel().setMap(((ExitTile) tile).getNextMap());
            player.setX(((ExitTile) tile).getNextX());
            player.setY(((ExitTile) tile).getNextY());
        } else if (tile.getClass().equals(EndGameTile.class)) {
            player.setVictory(true);
            Logger.getGlobal().info("VICTORY TILE");
            Configuration.getConfig().setGameOver(true);
            player.setX(player.getX());
        }
    }



}
