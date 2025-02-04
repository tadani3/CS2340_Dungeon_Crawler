package com.example.dungeongame;

import com.example.dungeongame.model.GameMap;
import com.example.dungeongame.model.Player;
import com.example.dungeongame.model.tileset.MapTile;
import com.example.dungeongame.viewmodels.GameViewModel;
import com.example.dungeongame.viewmodels.observation.Observer;
import com.example.dungeongame.viewmodels.strategies.ConfusedWalking;
import com.example.dungeongame.viewmodels.strategies.Walking;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests regarding player movement and movement strategies
 */
public class MovementUnitTests {
    private Player player;
    public static int value;
    GameMap map;
    GameViewModel gameViewModel;

    @Before
    public void initializeTests() {
        player = Player.getPlayer();
        player.setHP(100);
        value = 0;
        MapTile[][] tiles = new MapTile[10][10];
        tiles[0][0] = new MapTile(0, 0, 0);
        tiles[0][1] = new MapTile(1, 0, 1);
        tiles[0][2] = new MapTile(2, 0, 3);
        tiles[1][0] = new MapTile(0, 1, 4);
        map = new GameMap(tiles);
        player.setX(0);
        player.setY(0);
        gameViewModel = GameViewModel.getGameViewModel();
        gameViewModel.setMap(map);
    }

    /**
     * Test by Barry Walker
     */
    @Test
    public void settingMovementStrategy() {
        Walking walking = new Walking();
        ConfusedWalking confusedWalking = new ConfusedWalking();
        player.setMovementStrategy(walking);
        assertEquals(walking, player.getMovementStrategy());
        player.setMovementStrategy(confusedWalking);
        assertEquals(confusedWalking, player.getMovementStrategy());
    }

    /**
     * Test by Barry Walker
     */
    @Test
    public void testRenderObserverUpdates() {

        Observer playerObserver = new Observer() {
            @Override
            public void update() {
                MovementUnitTests.value++;
            }
        };
        player.addObserver(playerObserver);
        player.setX(player.getX() + 1);
        assertEquals(value, 1);
        player.setY(player.getY() - 1);
        assertEquals(value, 2);
        player.setX(player.getX() - 1);
        assertEquals(value, 3);
    }

    @Test
    public void playerMovementTest1() {
        Walking walking = new Walking();
        player.setMovementStrategy(walking);
        int originalx = player.getX();
        player.getMovementStrategy().moveRight();
        assertEquals(originalx + 1, player.getX());
    }

}