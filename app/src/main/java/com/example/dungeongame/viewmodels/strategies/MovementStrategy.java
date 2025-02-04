package com.example.dungeongame.viewmodels.strategies;

public interface MovementStrategy {
    abstract void moveLeft();

    abstract void moveRight();

    abstract void moveUp();

    abstract void moveDown();
}
