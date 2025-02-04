package com.example.dungeongame.viewmodels;

import com.example.dungeongame.model.Leaderboard;
import com.example.dungeongame.model.LeaderboardEntry;
import com.example.dungeongame.views.LeaderboardActivity;

import java.util.logging.Logger;

/**
 * View Model Class that controls the Leaderboard Logic.
 * ---
 * What is a View Model? Reusable logic code that links Model(s) and View(s) OR retrieves data from
 * Model(s) and exposes it to the View(s), where the model is specifically designed for the View.
 * ---
 * Use Cases:
 * 1. Player reaches end game and selects the "View Leaderboard" button.
 * 2. Player pauses the game and selects the "View Leaderboard" button.
 * ---
 * Models Linked:
 * 1. Leaderboard
 * 2. LeaderboardEntry
 * ---
 * Views Linked:
 * 1. LeaderboardActivity
 * ---
 * Design Pattern: MVVM
 * @author Ethan Nguyen-Tu, Connor Smith
 * @version 1.1.0
 */
public class LeaderboardViewModel extends PlayerAttemptViewModel {

    // Singleton Instances
    private static final Leaderboard LEADERBOARD = Leaderboard.getLeaderboard();

    /**
     * Provides log information when the LeaderboardViewModel is cleared.
     */
    @Override
    protected void onCleared() {
        Logger.getGlobal().info("LeaderboardViewModel cleared.");
    }

    /**
     * Updates the end screen leaderboard with the latest player entry.
     *
     * @param activity Activity where the leaderboard will be displayed
     * @return true if the update succeeds and false otherwise
     */
    public boolean updateLeaderboardUI(LeaderboardActivity activity) {
        try {
            // UPDATE LEADERBOARD
            String ranks = "";
            String allNames = "";
            String allScores = "";
            String allDates = "";

            LeaderboardEntry[] entries = LEADERBOARD.getEntries();
            for (int i = 0; i < LEADERBOARD.size(); i++) {
                ranks += (i + 1) + ".\n";
                allNames += String.format("%.12s", entries[i].getPlayerName()) + "\n";
                allScores += entries[i].getScore() + "\n";
                allDates += entries[i].getTimeStamp() + "\n";
            }

            activity.setLeaderboard(ranks, allNames, allScores, allDates);

            // Uncomment below line to get logger information about the current leaderboard state.
            Logger.getGlobal().info("Updated Leaderboard:\n" + LEADERBOARD);
        } catch (Exception exception) {
            return false;
        }
        return true;
    }
}
