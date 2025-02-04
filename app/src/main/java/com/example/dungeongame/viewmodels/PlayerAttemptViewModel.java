package com.example.dungeongame.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.dungeongame.model.Leaderboard;
import com.example.dungeongame.model.LeaderboardEntry;
import com.example.dungeongame.model.Player;
import com.example.dungeongame.views.PlayerAttemptView;

/**
 * View Model Class that controls the Player Attempt View Logic.
 * ---
 * What is a View Model? Reusable logic code that links Model(s) and View(s) OR retrieves data from
 * Model(s) and exposes it to the View(s), where the model is specifically designed for the View.
 * ---
 * Use Cases:
 * 1. View needs to display basic Player Attempt Information
 * ---
 * Models Linked:
 * 1. Player
 * 2. ViewModel
 * ---
 * Views Linked:
 * 1. PlayerAttemptView
 * 2. Game Activity
 * 3. Leaderboard Activity
 * 4. End Screen Activity
 * ---
 * Design Pattern: MVVM
 * @author Ethan Nguyen-Tu
 * @version 1.0.0
 */
public class PlayerAttemptViewModel extends ViewModel {

    // Singleton Instances
    private static final Player PLAYER = Player.getPlayer();
    private static final Leaderboard LEADERBOARD = Leaderboard.getLeaderboard();

    // Observable
    private final MutableLiveData<LeaderboardEntry> currentAttemptState =
            new MutableLiveData<>(PLAYER.getLeaderboardEntry());


    /**
     * Return the current attempt state.
     * @return LeaderboardEntry of the player's current attempt state
     */
    public LiveData<LeaderboardEntry> getCurrentAttemptState() {
        return currentAttemptState;
    }


    /**
     * Updates the currentAttemptState which informs the observers that the player attempt has been
     * updated.
     */
    public void updatePlayerAttempt() {
        if (PLAYER.getLeaderboardEntry().isEditable()) {
            currentAttemptState.setValue(Player.getPlayer().getLeaderboardEntry());
        }
    }


    /**
     * Updates the latest player entry display.
     *
     * @param activity Activity where the leaderboard will be displayed
     * @return true if the update succeeds and false otherwise
     */
    public boolean updatePlayerAttemptUI(PlayerAttemptView activity) {
        try {
            // UPDATE PREVIOUS ATTEMPT
            LeaderboardEntry attempt = PLAYER.getLeaderboardEntry();

            activity.setAttempt(
                    attempt.isEditable(),
                    LEADERBOARD.getRank(attempt) + ".",
                    String.format("%.12s", attempt.getPlayerName()),
                    "" + attempt.getScore(),
                    "" + attempt.getTimeStamp()
            );
        } catch (Exception exception) {
            return false;
        }
        return true;
    }

} // FIN
