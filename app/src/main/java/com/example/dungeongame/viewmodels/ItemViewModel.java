package com.example.dungeongame.viewmodels;

import com.example.dungeongame.model.GameMap;
import com.example.dungeongame.model.Player;

import com.example.dungeongame.model.powerups.PowerUp;
import com.example.dungeongame.model.powerups.PowerUpItem;
import com.example.dungeongame.views.GameActivity;

import java.util.List;

public class ItemViewModel {

    private static final Player PLAYER = Player.getPlayer();

    private List<PowerUpItem> items;
    private GameViewModel gameViewModel;
    private GameActivity gameActivity;


    /**
     * Initialize the item view model
     * @param activity the main gameActivity
     */
    public ItemViewModel(GameActivity activity) {
        items = GameMap.getActiveMap().getPowerUpItems();
        this.gameActivity = activity;
    }




    /**
     * Checks if the player's attack has collided with any of the items.
     */
    private void checkCollision() {
        PowerUp player = Player.getPlayer();
        for (PowerUpItem item : items) {
            if (item.isActive()) {
                item.checkCollision();
                items.remove(item);
            }
        }
    }
}
