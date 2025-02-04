package com.example.dungeongame.viewmodels;

import com.example.dungeongame.model.Clock;
import com.example.dungeongame.model.Player;
import com.example.dungeongame.views.EndScreenActivity;

import java.util.logging.Logger;

/**
 * View Model Class that controls the End Screen Logic.
 * ---
 * What is a View Model? Reusable logic code that links Model(s) and View(s) OR retrieves data from
 * Model(s) and exposes it to the View(s), where the model is specifically designed for the View.
 * ---
 * Use Cases:
 * 1. Player reaches end game or fails at some point in the game.
 * ---
 * Models Linked:
 * 1. LeaderboardEntry
 * 2. Player
 * ---
 * Views Linked:
 * 1. EndScreenActivity
 * ---
 * Design Pattern: MVVM
 * @author Ethan Nguyen-Tu
 * @version 1.1.0
 */
public class EndScreenViewModel extends PlayerAttemptViewModel {

    // Singleton Instances
    private static final Player PLAYER = Player.getPlayer();
    private static final Clock CLOCK = Clock.getClock();

    /**
     * Updates the end screen display with the latest Player Attempt information, Victory status,
     * and Character Sprite.
     *
     * @param activity Activity that controls the display of the End Screen information
     * @return true if the update succeeds and false otherwise
     */
    public boolean updateEndScreen(EndScreenActivity activity) {
        try {
            CLOCK.clear();

            if (PLAYER.getLeaderboardEntry().isEditable()) {
                PLAYER.getLeaderboardEntry().finalize();
                Logger.getGlobal().info("Player Attempt was finalized.");
            }

            // UPDATE PREVIOUS ATTEMPT
            updatePlayerAttemptUI(activity);

            // UPDATE GAME STATUS
            if (PLAYER.isVictory()) {
                activity.setGameStatus("VICTORY!", 0xFFFDB515);
            } else {
                activity.setGameStatus("Good Try.", 0xFF000000);
            }

            // UPDATE SPRITE
            activity.setSprite(PLAYER.getSpriteId());

            Logger.getGlobal().info("End Screen was updated.");

        } catch (Exception exception) {
            return false;
        }
        return true;
    }

} // FIN
