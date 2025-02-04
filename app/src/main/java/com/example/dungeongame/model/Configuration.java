package com.example.dungeongame.model;

import com.example.dungeongame.model.tileset.SpriteTileProvider;

import java.util.logging.Logger;

/**
 * Class that details all of the basic information for the game.
 * ---
 * Current Levels:
 * 1 = Easy
 * 2 = Medium
 * 3 = Hard
 * @author Ethan Nguyen-Tu
 * @version 1.0.0
 */
public class Configuration implements Resettable {

    // VARIABLES
    private static final Player PLAYER = Player.getPlayer();
    private static volatile Configuration config;

    // Game Status
    private boolean gameOver = false; // Boolean for if the game has ended or not

    // Difficulty
    private int difficulty; // Higher Number = Greater Difficulty
    private final String[] difficultyMapping = {"Not a translatable difficulty.", "Easy", "Medium",
        "Hard"}; // Maps String translation of difficulty number via the array index

    // Health
    private final int easyModeHealth = 100;
    private final int difficultyHealthDecrease = 25;

    //Score
    private final int easyModeScore = 100;
    private final int difficultyScoreIncrease = 50;


    // PUBLIC METHODS
    /**
     * Singleton implementation for the Configuration class.
     *
     * @return Configuration singleton instance
     */
    public static Configuration getConfig() {
        if (config == null) {
            synchronized (Configuration.class) {
                if (config == null) {
                    config = new Configuration();
                }
            }
        }
        return config;
    }

    /**
     * Convers the difficulty level to a string interpretation.
     * @return String version of difficulty level
     */
    public String getDifficultyString() {
        // Prevent array index out of bounds
        if (difficulty < 0 || difficulty > difficultyMapping.length) {
            return difficultyMapping[0];
        }

        return difficultyMapping[difficulty];
    }

    /**
     * Updates the stats of the player to match the current difficulty level.
     * @return boolean true if the update succeeds, else false
     */
    public boolean updatePlayer() {
        PLAYER.setMaxHP(easyModeHealth - ((difficulty - 1) * difficultyHealthDecrease));
        PLAYER.setHP(PLAYER.getMaxHP());
        PLAYER.setScore(easyModeScore + ((difficulty - 1) * difficultyScoreIncrease));
        return true;
    }

    /**
     * Resets the configuration object of the game to starter values
     * @return boolean true if the reset succeeds, else false
     */
    public boolean reset() {
        gameOver = false;
        difficulty = 0;
        SpriteTileProvider.getInstance().clearPlayerCache();
        Clock.getClock().reset();
        Logger.getGlobal().info("Configuration has been reset.");
        return true;
    }

    // GETTERS & SETTERS
    // Game Status
    /**
     * Getter for if the game has ended or not.
     * @return boolean true if the game has ended, else false
     */
    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    // Difficulty
    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

} // FIN
