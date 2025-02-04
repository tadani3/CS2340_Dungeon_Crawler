package com.example.dungeongame.views;

import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.example.dungeongame.R;

import java.util.logging.Logger;

/**
 * Activity that opens at the start of the game and allows the player to press start to begin the
 * game or exit to exit the game.
 * ---
 * FROM: Game Start
 * TO1: InitConfigActivity.java <- When "Start" is pressed.
 * TO2: Phone Main Screen <- When "Exit" is pressed.
 */
public class StartActivity extends AppCompatActivity {

    private Button startButton;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // getting UI components
        this.title = findViewById(R.id.gameTitle);
        this.startButton = findViewById(R.id.startButton);

        this.startButton.setOnClickListener(v -> onStartButtonClicked());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.getGlobal().info("StartActivity is destroyed.");
    }

    private void onStartButtonClicked() {
        // will go to game config screen
        startActivity(new Intent(this, InitConfigActivity.class));
        finish();
    }
}