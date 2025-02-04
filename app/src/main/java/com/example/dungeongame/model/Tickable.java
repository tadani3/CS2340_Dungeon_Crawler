package com.example.dungeongame.model;

/**
 * Interface that determines if something is able to be added to the clock.
 */
public interface Tickable {

    /**
     * Method that is called every tick of the game. Game ticks occur every 100ms.
     * This should be used for anything that needs to be periodically assessed, such as
     * enemy/player damage, enemy spawning, etc.
     */
    void onTick();

} // FIN
