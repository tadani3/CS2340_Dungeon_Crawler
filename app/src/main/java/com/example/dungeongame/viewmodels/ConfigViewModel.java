package com.example.dungeongame.viewmodels;

import com.example.dungeongame.model.Configuration;
import com.example.dungeongame.model.Player;
import com.example.dungeongame.viewmodels.observation.CollisionObserver;
import com.example.dungeongame.views.GameActivity;
import com.example.dungeongame.views.InitConfigActivity;

public class ConfigViewModel {

    private static final Player PLAYER = Player.getPlayer();
    private static final Configuration CONFIG = Configuration.getConfig();

    public ConfigViewModel() {
    }

    public static void populateInterface(GameActivity activity) {
        activity.setPlayerName(PLAYER.getPlayerName());
        activity.setPlayerScore(PLAYER.getScore());
        activity.setDifficulty(CONFIG.getDifficultyString());
        activity.setCurrentHP(PLAYER.getHP());
    }

    /**
     * Read the selections on the player config menu and fill in the fields of the player model.
     * @param activity The configuration activity
     * @return Whether the config was successfully filled or not
     */
    public static boolean evaluateConfig(InitConfigActivity activity) {
        CONFIG.reset();

        String inputName = activity.getInputText();
        if (PLAYER.setPlayerName(inputName)) {
            activity.setAnswerText("Good name choice.");
        } else {
            activity.setAnswerText("Must provide a valid name.");
            return false;
        }

        //Check the difficulty and sets the variables accordingly
        switch (activity.getDifficultySelection()) {
        case "easy":
            CONFIG.setDifficulty(1);
            break;
        case "medium":
            CONFIG.setDifficulty(2);
            break;
        default:
            CONFIG.setDifficulty(3);
            break;
        }

        CONFIG.updatePlayer();
        PLAYER.setPlayerName(activity.getInputText());
        PLAYER.setSpriteId(activity.getSpriteSelection());

        PLAYER.removeObserver(CollisionObserver.getColObserver());
        PLAYER.addObserver(CollisionObserver.getColObserver());
      
        return true;
    }

} // FIN
