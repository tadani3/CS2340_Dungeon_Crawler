package com.example.dungeongame.views;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dungeongame.R;

/**
 * A abstract view that specifies the player's last attempt's layout and visuals.
 * ---
 * What is a view? A view is responsible for defining the structure, layout, and appearance of what
 * the user sees on screen.
 * ---
 * Child Views:
 * 1. LeaderboardActivity
 * 2. EndScreenActivity
 * 3. PauseMenu
 * ---
 * Design Pattern: MVVM
 * @author Ethan Nguyen-Tu
 * @version 1.0.0
 */
public abstract class PlayerAttemptView extends AppCompatActivity {

    // Previous Attempt Variables
    private TextView attemptTitle;
    private TextView attemptRank;
    private TextView attemptPlayerName;
    private TextView attemptScore;
    private TextView attemptDateTime;


    // SETTERS
    /**
     * Setter for the previous attempt field that enters all relevant values
     *
     * @param title Boolean true if previous attempt or false if current attempt
     * @param rank String rank of the attempt
     * @param name String name of the player
     * @param score String score of the player
     * @param dateTime String date/time of the attempt
     */
    public void setAttempt(boolean title, String rank, String name, String score,
                           String dateTime) {
        attemptTitle.setText(getString(title ? R.string.label_currentAttempt
                : R.string.label_previousAttempt));
        attemptRank.setText(rank);
        attemptPlayerName.setText(name);
        attemptScore.setText(score);
        attemptDateTime.setText(dateTime);
    }


    /**
     * Setter for the title of the player's attempt TextView.
     * @param attemptTitle Textview to set the player's attempt title to
     */
    public void setAttemptTitle(TextView attemptTitle) {
        this.attemptTitle = attemptTitle;
    }


    /**
     * Setter for the rank of the player's attempt TextView.
     * @param attemptRank Textview to set the player's attempt rank to
     */
    public void setAttemptRank(TextView attemptRank) {
        this.attemptRank = attemptRank;
    }


    /**
     * Setter for the player name of the player's attempt TextView.
     * @param attemptPlayerName Textview to set the player's attempt player name to
     */
    public void setAttemptPlayerName(TextView attemptPlayerName) {
        this.attemptPlayerName = attemptPlayerName;
    }


    /**
     * Setter for the score of the player's attempt TextView.
     * @param attemptScore Textview to set the player's attempt score to
     */
    public void setAttemptScore(TextView attemptScore) {
        this.attemptScore = attemptScore;
    }


    /**
     * Setter for the date-time of the player's attempt TextView.
     * @param attemptDateTime Textview to set the player's attempt date-time to
     */
    public void setAttemptDateTime(TextView attemptDateTime) {
        this.attemptDateTime = attemptDateTime;
    }

} // FIN
