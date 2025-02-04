package com.example.dungeongame.viewmodels.observation;

import com.example.dungeongame.model.GameMap;
import com.example.dungeongame.model.enemies.Enemy;
import com.example.dungeongame.model.powerups.PowerUpItem;

import java.util.ArrayList;

public class CollisionObserver implements Observer {
    private static CollisionObserver instance;

    private CollisionObserver() {
        // nothing
    }

    public static CollisionObserver getColObserver() {
        if (instance == null) {
            instance = new CollisionObserver();
        }
        return instance;
    }

    public void update() {
        // a copy is needed to avoid concurrent modification exceptions, which can happen if
        // the collision triggers the death of an enemy
        if (GameMap.getActiveMap() == null) {
            return;
        }

        ArrayList<Enemy> enemiesCopy = new ArrayList<>(GameMap.getActiveMap().getEnemies());
        ArrayList<PowerUpItem> powersCopy =
                new ArrayList<>(GameMap.getActiveMap().getPowerUpItems());
        for (Enemy enemy : enemiesCopy) {
            enemy.checkCollision();
        }
        for (PowerUpItem item : powersCopy) {
            item.checkCollision();

        }
    }
}
