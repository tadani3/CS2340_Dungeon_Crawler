package com.example.dungeongame.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.dungeongame.viewmodels.ConfigViewModel;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dungeongame.R;

import java.util.logging.Logger;

/**
 * Activity that details the initial game configurations for the player to choose from.
 * ---
 * FROM: StartActivity.java
 * TO: GameActivity.java <- When "Select Choices" is pressed.
 */
public class InitConfigActivity extends AppCompatActivity {

    private EditText inputText;
    private TextView inputAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init_config);
        Button selectButton = findViewById(R.id.select_choices_button);
        inputText = (EditText) findViewById(R.id.input_text);
        inputAnswer = (TextView) findViewById(R.id.inputAnswer);

        selectButton.setOnClickListener(v -> { // Select Choices Button
            if (ConfigViewModel.evaluateConfig(this)) {
                Intent game = new Intent(this, GameActivity.class);
                game.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(game, 0);
                finish();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.getGlobal().info("InitConfigActivity is destroyed.");
    }

    // Getters
    public String getInputText() {
        return inputText.getText().toString();
    }

    /**
     * @return String of the player-selected difficulty.
     */
    public String getDifficultySelection() {
        RadioGroup difficultyRadioGroup = findViewById(R.id.difficulty_radio_group);
        if (difficultyRadioGroup.getCheckedRadioButtonId() == (R.id.radio_easy)) {
            return "easy";
        } else if (difficultyRadioGroup.getCheckedRadioButtonId()
                == (R.id.radio_medium)) {
            return "medium";
        } else if (difficultyRadioGroup.getCheckedRadioButtonId()
                == (R.id.radio_hard)) {
            return "hard";
        } else {
            return "easy";
        }
    }


    /**
     * @return int id of the player-selected sprite.
     */
    public int getSpriteSelection() {
        RadioGroup spriteRadioGroup = findViewById(R.id.sprite_radio_group);
        if (spriteRadioGroup.getCheckedRadioButtonId() == (R.id.radio_luigi)) {
            return 1;
        } else if (spriteRadioGroup.getCheckedRadioButtonId() == (R.id.radio_mario)) {
            return 2;
        } else if (spriteRadioGroup.getCheckedRadioButtonId() == (R.id.radio_bowser)) {
            return 3;
        }
        return 1;
    }

    // Setters

    /**
     * Setter for the answer text
     * @param text String text to set the answer to
     */
    public void setAnswerText(String text) {
        inputAnswer.setText(text);
    }

} // FIN
