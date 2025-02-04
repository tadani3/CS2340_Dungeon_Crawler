package com.example.dungeongame.viewmodels.observation;

import com.example.dungeongame.model.Player;
import com.example.dungeongame.views.GameActivity;

public class RenderObserver implements Observer {
    private GameActivity gameActivity;

    public RenderObserver(GameActivity activity) {
        gameActivity = activity;
        Player.getPlayer().addObserver(this);
    }

    public void update() {
        gameActivity.rerenderPlayer();
    }

}
