package com.example.dungeongame;

import com.example.dungeongame.model.Player;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicInteger;

public class ScoreUnitTests {

    @Test
    public void testScoreReset() {
        Player player = Player.getPlayer();
        player.resetScore(2340);
        assertEquals(2340, player.getScore());
    }

    @Test
    public void testScoreAdd() {
        Player player = Player.getPlayer();
        player.resetScore(2340);
        player.addScore(100);
        assertEquals(2440, player.getScore());
    }

    @Test
    public void testScoreCallback() {
        AtomicInteger callbackScore = new AtomicInteger();

        Player player = Player.getPlayer();
        player.resetScore(2340);
        player.setScoreUpdateCallback(() -> {
            callbackScore.set(player.getScore());
        });
        player.addScore(100);

        assertEquals(2440, callbackScore.get());
    }
}
