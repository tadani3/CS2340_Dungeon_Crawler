package com.example.dungeongame;

import com.example.dungeongame.model.Player;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit tests created for the Player Singleton class. The verifyPlayerInitialization(),
 * testPlayerSingleton(), testPlayerMovement() and testPlayerName() tests were created by
 * Tommaso Adani.
 *
 * @author Tommaso Adani
 * @version 1.0.0
 */
public class PlayerUnitTests {
    private Player player;

    @Before
    public void verifyPlayerInitialization() {
        player = Player.getPlayer();
    }

    @Test
    public void testPlayerSingleton() {
        Player player2 = Player.getPlayer();
        assert (player.equals(player2));
    }

    @Test
    public void testPlayerName() {
        player.setPlayerName("Mario");
        assert (player.getPlayerName().equals("Mario"));
        player.setPlayerName("Link");
        assert (player.getPlayerName().equals("Link"));
    }
    /**
     * Junit tests by Connor Smith below.
     */
    @Test
    public void testSpriteID() {
        player.setSpriteId(100);
        assert (player.getSpriteId() == 100);
        player.setSpriteId(10101010);
        assert (player.getSpriteId() == 10101010);
    }

    @Test
    public void testPlayerResetScore() {
        player.resetScore(10);
        assertEquals(10, player.getScore());
        player.resetScore(0);
        assertEquals(0, player.getScore());
        player.resetScore(11);
        assertEquals(11, player.getScore());
        player.resetScore(100);
        assertEquals(100, player.getScore());
    }

    /** Connor Smith Unit Tests for Sprint 4
     *
     */
    @Test
    public void testSetVictory() {
        player.setVictory(true);
        assertEquals(true, player.isVictory());
        player.setVictory(false);
        assertEquals(false, player.isVictory());
    }

    @Test
    public void testPlayerAddScore() {
        player.resetScore(10);
        player.addScore(10);
        assertEquals(20, player.getScore());
        player.addScore(10);
        assertEquals(30, player.getScore());
        player.addScore(10);
        assertEquals(40, player.getScore());
        player.addScore(-10);
        assertEquals(30, player.getScore());
    }

    /**
     * jUnit tests created by Barry Walker
     */
    @Test
    public void testPlayerHP() {
        player.setHP(120);
        assertEquals(120, player.getHP());
        player.setHP(145);
        assertEquals(145, player.getHP());
    }

    @Test
    public void testPlayerNameValidity() {
        assertFalse(player.setPlayerName(""));
        assertFalse(player.setPlayerName("    "));
        assertTrue(player.setPlayerName("  a  "));
        assertTrue(player.setPlayerName("apple"));
    }

    @Test
    public void testPlayerMovement() {
        int oldX = player.getX();
        int oldY = player.getY();

        player.setX(oldX + 1);
        assert (player.getX() == oldX + 1);
        assert (player.getY() == oldY);

        player.setY(oldY + 1);
        assert (player.getY() == oldY + 1);
        assert (player.getX() == oldX + 1);

        int newX = player.getX();
        int newY = player.getY();

        player.setX(newX - 1);
        assert (player.getX() == oldX);
        assert (player.getY() == oldY + 1);

        player.setY(newY - 1);
        assert (player.getX() == oldX);
        assert (player.getY() == oldY);
    }

    /**
     * Test created by Tommaso Adani to ensure that the Player's HP doesn't fall to a negative value
     * if damage is taken.
     */
    @Test
    public void testHPAtZero() {
        player.setHP(4);
        int afterHP = Player.getPlayer().getHP() - 10;
        assert (player.getHP() == 4);
    }

    /**
     * Test created by Tommaso Adani to ensure that the Player's HP falls correctly
     * if damage is taken.
     */
    @Test
    public void testHPAboveZero() {
        player.setHP(67);
        int afterHP = Player.getPlayer().getHP() - 10;
        player.setHP(afterHP);
        assert (player.getHP() == 57);
    }

    /**
     * Test to ensure that a player's score decreases whenever their HP decreases.
     * Daniel Cooper
     */
    @Test
    public void testTakeDamage() {
        player.setHP(100);
        player.resetScore(100);
        player.takeDamage(55);
        assertTrue("Score should have decreased by some amount",
                player.getScore() < 100);
        assertEquals(45, player.getHP());
    }
}