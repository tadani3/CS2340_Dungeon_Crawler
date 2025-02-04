package com.example.dungeongame;

import static org.junit.Assert.assertEquals;

import com.example.dungeongame.model.Configuration;

import org.junit.Before;
import org.junit.Test;

public class ConfigurationTests {
    private Configuration CONFIG;

    @Before
    public void testConfigurationInitialization() {
        CONFIG = Configuration.getConfig();
    }

    @Test
    public void testPlayerDiffEasy() {
        CONFIG.setDifficulty(1);
        assertEquals("Easy", CONFIG.getDifficultyString());
    }

    @Test
    public void testPlayerDiffMedium() {
        CONFIG.setDifficulty(2);
        assertEquals("Medium", CONFIG.getDifficultyString());
    }

    @Test
    public void testPlayerDiffHard() {
        CONFIG.setDifficulty(3);
        assertEquals("Hard", CONFIG.getDifficultyString());
    }
}
