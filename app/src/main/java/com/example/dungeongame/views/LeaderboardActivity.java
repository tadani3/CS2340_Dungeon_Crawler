
package com.example.dungeongame.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.example.dungeongame.R;
import com.example.dungeongame.viewmodels.LeaderboardViewModel;

import java.util.logging.Logger;

/**
 * A view that defines the leaderboard activity's layout and visuals.
 * ---
 * What is a view? A view is responsible for defining the structure, layout, and appearance of what
 * the user sees on screen.
 * ---
 * View Model Linked:
 * LeaderboardViewModel
 * ---
 * Design Pattern: MVVM
 * @author Ethan Nguyen-Tu, Connor Smith
 * @version 1.3.0
 */
public class LeaderboardActivity extends PlayerAttemptView {

    // Leaderboard Variables
    private TextView lbRank;
    private TextView lbPlayerName;
    private TextView lbScore;
    private TextView lbDateTime;

    // VIEW MODEL
    private LeaderboardViewModel model;

    /**
     * Loads the leaderboard.xml layout and propagates it with the information given by the
     * LeaderboardViewModel.java view model. Information details the leaderboard entries in
     * the Leaderboard.java singleton class file and LeaderboardEntry of the Player.java singleton
     * file.
     *
     * @param savedInstance Bundle mapping string keys to various parcelable values
     */
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        setContentView(R.layout.leaderboard);

        // Leaderboard Variables
        lbRank = findViewById(R.id.header_lbRank);
        lbPlayerName = findViewById(R.id.header_lbPlayerName);
        lbScore = findViewById(R.id.header_lbScore);
        lbDateTime = findViewById(R.id.header_lbDateTime);

        // Previous Attempt Variables
        setAttemptTitle(findViewById(R.id.title_prevAttempt));
        setAttemptRank(findViewById(R.id.header_paRank));
        setAttemptPlayerName(findViewById(R.id.header_paPlayerName));
        setAttemptScore(findViewById(R.id.header_paScore));
        setAttemptDateTime(findViewById(R.id.header_paDateTime));

        // View Model
        model = new ViewModelProvider(this).get(LeaderboardViewModel.class);

        model.getCurrentAttemptState().observe(this, currentAttemptState -> {
            model.updatePlayerAttemptUI(this);
            model.updateLeaderboardUI(this);
            Logger.getGlobal().info("Leaderboard Activity PlayerAttempt change observed.");
        });

        // Back Button
        Button backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(v -> onBackButtonClicked());

        // Restart Button
        Button restartButton = findViewById(R.id.button_restart);
        restartButton.setOnClickListener(v -> onRestartButtonClicked());
    }

    /**
     * Invoked when the activity enters the Resumed state where it comes to the foreground and the
     * app interacts with the user.
     * ---
     * The app stays in this state until something happens to take focus away from the app, such as
     * the device receiving a phone call, the user navigating to another activity, or the device
     * screen turning off.
     */
    @Override
    protected void onResume() {
        super.onResume();
        model.updatePlayerAttempt();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.getGlobal().info("LeaderboardActivity is destroyed.");
    }

    /**
     * Button that takes the player back to the EndScreenActivity.
     */
    private void onBackButtonClicked() {
        finish();
    }

    /**
     * Button that allows the player to restart the game and complete a new run.
     */
    private void onRestartButtonClicked() {
        Intent restart = new Intent(this, StartActivity.class);
        restart.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivityIfNeeded(restart, 0);
        finish();
    }

    /**
     * Setter for the leaderboard field that enters all attempts and their relevant values
     *
     * @param ranks String ranks of all attempts
     * @param names String names of the players
     * @param scores String scores of the players
     * @param dates String dates/times of the attempts
     */
    public void setLeaderboard(String ranks, String names, String scores, String dates) {
        lbRank.setText(ranks);
        lbPlayerName.setText(names);
        lbScore.setText(scores);
        lbDateTime.setText(dates);
    }

} // FIN
