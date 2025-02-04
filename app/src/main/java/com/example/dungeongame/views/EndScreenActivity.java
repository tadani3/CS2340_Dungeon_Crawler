package com.example.dungeongame.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.example.dungeongame.R;
import com.example.dungeongame.viewmodels.EndScreenViewModel;

import java.util.logging.Logger;

/**
 * A view that defines the end screen activity's layout and visuals.
 * ---
 * What is a view? A view is responsible for defining the structure, layout, and appearance of what
 * the user sees on screen.
 * ---
 * View Model Linked:
 * EndScreenViewModel
 * ---
 * Design Pattern: MVVM
 * @author Ethan Nguyen-Tu, Connor Smith
 * @version 1.3.0
 */
public class EndScreenActivity extends PlayerAttemptView {

    // GAME STATUS
    private TextView gameStatus;

    // CHARACTER
    private ImageView sprite;

    // VIEW MODEL
    private EndScreenViewModel model;

    /**
     * Loads the end_screen.xml layout and propagates it with the information given by the
     * EndScreenViewModel.java view model. Information details the Player's previous attempt in
     * the Leaderboard.java singleton class file via the LeaderboardEntry of the Player.java
     * singleton file.
     *
     * @param savedInstance Bundle mapping string keys to various parcelable values
     */
    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        setContentView(R.layout.end_screen);

        // Previous Attempt
        setAttemptTitle(findViewById(R.id.header_attempt));
        setAttemptRank(findViewById(R.id.header_attemptRank));
        setAttemptPlayerName(findViewById(R.id.header_attemptPlayerName));
        setAttemptScore(findViewById(R.id.header_attemptScore));
        setAttemptDateTime(findViewById(R.id.header_attemptDateTime));

        // Game Status
        gameStatus = findViewById(R.id.es_status);

        // Character Sprite
        sprite = findViewById(R.id.es_sprite);

        // View Model
        model = new ViewModelProvider(this).get(EndScreenViewModel.class);
        model.getCurrentAttemptState().observe(this, currentAttemptState -> {
            model.updatePlayerAttemptUI(this);
            Logger.getGlobal().info("PlayerAttempt change observed.");
        });

        // Leaderboard Button
        Button viewLeaderboardButton = findViewById(R.id.es_viewLeaderboard_button);
        viewLeaderboardButton.setOnClickListener(v -> onViewLeaderboardButtonClicked());

        // Restart Button
        Button restartButton = findViewById(R.id.es_restartButton);
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
        model.updateEndScreen(this);
    }

    /**
     * Button that takes the player back to the LeaderboardActivity.
     */
    private void onViewLeaderboardButtonClicked() {
        Intent leaderboard = new Intent(this, LeaderboardActivity.class);
        leaderboard.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(leaderboard, 0);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.getGlobal().info("EndScreenActivity is destroyed.");
    }

    // SETTERS
    /**
     * Setter for the end screen title for the Victory or Defeat status of the player.
     *
     * @param status String of the status title
     * @param textColor int color of the status title
     */
    public void setGameStatus(String status, int textColor) {
        gameStatus.setText(status);
        gameStatus.setTextColor(textColor);
    }

    /**
     * Setter for the character sprite display. Default is the question mark.
     *
     * @param id int of the character sprite resource id
     */
    public void setSprite(int id) {
        switch (id) {
        case 1:
            sprite.setImageResource(R.drawable.spriteplayer1);
            break;
        case 2:
            sprite.setImageResource(R.drawable.spriteplayer2);
            break;
        case 3:
            sprite.setImageResource(R.drawable.spriteplayer3);
            break;
        default:
            sprite.setImageResource(R.drawable.question_mark);
            break;
        }
    }

} // FIN