package com.example.dungeongame.views;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.dungeongame.R;
import com.example.dungeongame.model.Clock;
import com.example.dungeongame.viewmodels.PlayerAttemptViewModel;

import java.util.logging.Logger;

/**
 * An abstract view that defines the pause menu's layout and visuals.
 * ---
 * What is a view? A view is responsible for defining the structure, layout, and appearance of what
 * the user sees on screen.
 * ---
 * View Model Linked:
 * PlayerAttemptViewModel
 * ---
 * Child Views:
 * Game Activity
 * ---
 * Design Pattern: MVVM
 * @author Ethan Nguyen-Tu, Connor Smith
 * @version 1.0.0
 */
public abstract class PauseMenu extends PlayerAttemptView {
    private static final Clock CLOCK = Clock.getClock();
    private View pauseMenuView;
    private PlayerAttemptViewModel model;

    /**
     * Method that sets up the pause menu and should be called within an activity's onCreate()
     * method.
     */
    public void setUpPauseMenu() {
        // PAUSE MENU
        LayoutInflater pauseInflater = getLayoutInflater();
        pauseMenuView = pauseInflater.inflate(R.layout.pause_menu, null);

        pauseMenuView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        // Pause Menu Variables
        ConstraintLayout mainLayout = findViewById(R.id.gameScreenRootLayout);
        mainLayout.addView(pauseMenuView);
        pauseMenuView.setVisibility(View.GONE); // Start with pause menu hidden

        // Player Attempt
        setAttemptTitle(findViewById(R.id.pause_title_attempt));
        setAttemptRank(findViewById(R.id.pause_header_attemptRank));
        setAttemptPlayerName(findViewById(R.id.pause_header_attemptPlayerName));
        setAttemptScore(findViewById(R.id.pause_header_attemptScore));
        setAttemptDateTime(findViewById(R.id.pause_header_attemptDateTime));

        model = new ViewModelProvider(this).get(PlayerAttemptViewModel.class);
        model.getCurrentAttemptState().observe(this, currentAttemptState -> {
            model.updatePlayerAttemptUI(this);
            Logger.getGlobal().info("Pause Menu player attempt updated.");
        });

        // Resume Button
        ImageView backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(v -> togglePauseMenu());

        // Leaderboard Button
        Button viewLeaderboardButton = findViewById(R.id.button_viewLeaderboard);
        viewLeaderboardButton.setOnClickListener(v -> onViewLeaderboardButtonClicked());

        // Resign Button
        Button resignButton = findViewById(R.id.button_resign);
        resignButton.setOnClickListener(v -> onResignButtonClicked());

        // Resume Button
        Button resumeButton = findViewById(R.id.button_resume);
        resumeButton.setOnClickListener(v -> togglePauseMenu());
    }

    /**
     * Requests the observable to update the Player Attempt to the latest information.
     */
    public void updatePauseMenu() {
        model.updatePlayerAttempt();
    }

    /**
     * Method that takes the player to the LeaderboardActivity when the button labeled "View
     * Leaderboard" is clicked.
     */
    private void onViewLeaderboardButtonClicked() {
        Intent leaderboard = new Intent(this, LeaderboardActivity.class);
        leaderboard.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivityIfNeeded(leaderboard, 0);
    }

    /**
     * Method that toggles the pause menu visibility.
     */
    private void togglePauseMenu() {
        updatePauseMenu();
        if (pauseMenuView.getVisibility() == View.GONE) {
            pauseMenuView.setVisibility(View.VISIBLE);
            CLOCK.pause();
        } else {
            pauseMenuView.setVisibility(View.GONE);
            CLOCK.start();
        }
        Logger.getGlobal().info("PauseMenu toggled.");
    }

    /**
     * Method that takes the player to the end screen.
     */
    private void onResignButtonClicked() {
        Intent resign = new Intent(this, EndScreenActivity.class);
        resign.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivityIfNeeded(resign, 0);
        finish();
    }
}
