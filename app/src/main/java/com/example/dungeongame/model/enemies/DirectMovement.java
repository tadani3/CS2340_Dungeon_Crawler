package com.example.dungeongame.model.enemies;

import com.example.dungeongame.model.Player;

public class DirectMovement extends AutomatedMovementStrategy {

    private Enemy enemy;

    public DirectMovement(Enemy enemy) {
        this(enemy, 4);
    }

    public DirectMovement(Enemy enemy, int ticksPerMove) {
        super();
        this.enemy = enemy;
        this.ticksPerMove = ticksPerMove;
    }

    @Override
    void move() {
        // moves one tile closer to the player, no matter the boundaries
        Player player = Player.getPlayer();

        int xDiff = player.getX() - enemy.getX();
        int yDiff = player.getY() - enemy.getY();

        enemy.notifyObservers();

        if (Math.abs(xDiff) == 1 && yDiff == 0 || Math.abs(yDiff) == 1 && xDiff == 0) {
            return; // don't move if already adjacent
        }

        // move in the direction of the player, but only by one tile
        if (Math.abs(xDiff) > Math.abs(yDiff)) {
            if (xDiff > 0) {
                enemy.setX(enemy.getX() + 1);
            } else {
                enemy.setX(enemy.getX() - 1);
            }
        } else {
            if (yDiff > 0) {
                enemy.setY(enemy.getY() + 1);
            } else {
                enemy.setY(enemy.getY() - 1);
            }
        }
    }
}
