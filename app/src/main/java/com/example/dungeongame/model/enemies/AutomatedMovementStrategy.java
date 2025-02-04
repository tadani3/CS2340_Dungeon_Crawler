package com.example.dungeongame.model.enemies;

import com.example.dungeongame.model.Clock;
import com.example.dungeongame.model.Tickable;

public abstract class AutomatedMovementStrategy implements Tickable {
    private static final Clock CLOCK = Clock.getClock();
    protected int ticksPerMove = 4;
    private int ticksSinceLastMove = 0;

    public AutomatedMovementStrategy() {
        CLOCK.add(this); // Add this strategy to the clock
    }

    /**
     * Invoked every time the enemy is supposed to make a move. The enemy's movements should be
     * grid-aligned and it should only move progressively towards its target, not large jumps.
     */
    abstract void move();

    /**
     * Removes the strategy from the clock when the enemy is destroyed.
     */
    public void finalizeTicks() {
        CLOCK.remove(this); // Remove this strategy from the clock
    }

    @Override
    public void onTick() {
        // should move every TICKS_PER_MOVE ticks
        if (ticksSinceLastMove < this.ticksPerMove) {
            ticksSinceLastMove++;
            return;
        }

        ticksSinceLastMove = 0;

        // perform the movement
        this.move();
    }

}
