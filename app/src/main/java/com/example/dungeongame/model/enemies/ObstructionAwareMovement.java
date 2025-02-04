package com.example.dungeongame.model.enemies;

import com.example.dungeongame.model.Player;
import com.example.dungeongame.model.tileset.WallTile;

public class ObstructionAwareMovement extends AutomatedMovementStrategy {
    private Enemy enemy;

    public ObstructionAwareMovement(Enemy enemy) {
        super();
        this.enemy = enemy;
    }

    @Override
    void move() {
        // enemies aren't that clever, so will move until it hits a wall. When it does, it will
        // try to move in a different direction while still approaching the player.

        Player player = Player.getPlayer();

        int xDiff = player.getX() - enemy.getX();
        int yDiff = player.getY() - enemy.getY();

        enemy.notifyObservers();

        if (Math.abs(xDiff) == 1 && yDiff == 0 || Math.abs(yDiff) == 1 && xDiff == 0) {
            return; // don't move if already adjacent
        }

        // move in the direction of the player, but only by one tile
        if (xDiff > 0) {
            // checking for obstacles
            if (enemy.getMap().getTileAt(enemy.getX() + 1, enemy.getY()).getClass()
                    != WallTile.class) {
                enemy.setX(enemy.getX() + 1);
            }
        } else {
            if (enemy.getMap().getTileAt(enemy.getX() - 1, enemy.getY()).getClass()
                    != WallTile.class) {
                enemy.setX(enemy.getX() - 1);
            }
        }

        if (yDiff > 0) {
            // checking for obstacles
            if (enemy.getMap().getTileAt(enemy.getX(), enemy.getY() + 1).getClass()
                    != WallTile.class) {
                enemy.setY(enemy.getY() + 1);
            }
        } else {
            if (enemy.getMap().getTileAt(enemy.getX(), enemy.getY() - 1).getClass()
                    != WallTile.class) {
                enemy.setY(enemy.getY() - 1);
            }
        }
    }
}
