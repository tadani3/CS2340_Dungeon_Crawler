package com.example.dungeongame.model;

import androidx.annotation.VisibleForTesting;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;


/**
 * A clock implementation that keeps track of everything in the current game instance that moves
 * every tick of the game.
 * ---
 * Design Pattern: Singleton
 * @author Ethan Nguyen-Tu
 * @version 1.0.0
 */
public class Clock implements Pauseable, Resettable {
    private static volatile Clock clock;
    private boolean ticking = true;
    private int tickSpeed = 100;

    private final ArrayList<Tickable> tickables = new ArrayList<>();

    // Latency Variables
    private boolean wait = false;
    private final ConcurrentLinkedQueue<Tickable> addWait = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Tickable> removeWait = new ConcurrentLinkedQueue<>();


    // PUBLIC METHODS
    /**
     * Singleton implementation for the Leaderboard class.
     *
     * @return Leaderboard singleton instance
     */
    public static Clock getClock() {
        if (clock == null) {
            synchronized (Clock.class) {
                if (clock == null) {
                    clock = new Clock();
                }
            }
        }
        return clock;
    }


    public void pause() {
        this.ticking = false;
        Logger.getGlobal().info("Clock Paused");
    }


    public void start() {
        this.ticking = true;
        Logger.getGlobal().info("Clock Started");
    }


    /**
     * Method that iterates all tickable objects by a tick.
     */
    public void tick() {
        wait = true;
        if (ticking) {
            for (Tickable t : tickables) {
                t.onTick();
            }
            Logger.getGlobal().info("Ticked " + tickables.size());
        }
        wait = false;
        while (!addWait.isEmpty()) {
            add(addWait.poll());
        }
        while (!removeWait.isEmpty()) {
            remove(removeWait.poll());
        }
    }


    /**
     * Method that adds a tickable object to the clock.
     * @param t Tickable to be added to the clock
     */
    public void add(Tickable t) {
        if (wait) {
            addWait.add(t);
        } else {
            tickables.add(t);
            Logger.getGlobal().info("Added: " + t);
        }
    }


    /**
     * Method that removes a tickable object from the clock.
     * @param t Tickable to be removed from the clock
     */
    public void remove(Tickable t) {
        if (wait) {
            removeWait.add(t);
        } else {
            tickables.remove(t);
            Logger.getGlobal().info("Removed: " + t);
        }
    }


    /**
     * Method that clears the clock of all tickable entries.
     */
    public void clear() {
        tickables.clear();
        Logger.getGlobal().info("Clock was cleared.");
    }


    /**
     * Getter for if the clock is ticking.
     * @return Boolean true if the clock is ticking, else false
     */
    public boolean getTicking() {
        return ticking;
    }


    /**
     * Method that returns the tick speed of the clock.
     * @return int of the clock's tick speed
     */
    public int getTickSpeed() {
        return this.tickSpeed;
    }


    // RESETTABLE
    @Override
    public boolean reset() {
        clear();
        start();
        return true;
    }


    // UNIT TEST METHODS
    /**
     * Method that returns all tickables tracked for the purpose of unit testing.
     * @return ArrayList of all tickables
     */
    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    public ArrayList<Tickable> getTickables() {
        return tickables;
    }

} // FIN
