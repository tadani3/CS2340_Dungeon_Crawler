package com.example.dungeongame;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;


import android.util.DisplayMetrics;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.dungeongame.model.GameMap;
import com.example.dungeongame.model.tileset.MapTile;
import com.example.dungeongame.viewmodels.GameViewModel;
import com.example.dungeongame.viewmodels.GraphicTile;

/**
 * Includes both tests of the map and game view model functions pertaining to the map.
 */
public class GameMapTest {

    @Test
    public void testMap() {
        MapTile[][] tiles = new MapTile[10][10];
        tiles[0][0] = new MapTile(0, 0, 0);
        tiles[0][1] = new MapTile(1, 0, 1);
        tiles[0][2] = new MapTile(2, 0, 3);
        tiles[1][0] = new MapTile(0, 1, 4);
        GameMap map = new GameMap(tiles);

        assertEquals(0, map.getTileAt(0, 0).getId());
        assertEquals(4, map.getTileAt(0, 1).getId());
        assertEquals(1, map.getTileAt(1, 0).getId());
        assertEquals(3, map.getTileAt(2, 0).getId());
    }

    @Test
    public void testGameViewModelTileWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        metrics.widthPixels = 1280;
        metrics.heightPixels = 2560;

        GameViewModel viewModel = GameViewModel.getGameViewModel();
        viewModel.configureDisplay(metrics);

        assertEquals(128, viewModel.getTileWidth());
    }

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();
    @Test
    public void testGameViewCameraUpdate() {
        MapTile[][] tiles = new MapTile[1][20];
        for (int i = 0; i < 20; i++) {
            tiles[0][i] = new MapTile(0, 0, i);
        }

        GameMap map = new GameMap(tiles);

        DisplayMetrics metrics = new DisplayMetrics();
        metrics.widthPixels = 1280;
        metrics.heightPixels = 2560;

        GameViewModel viewModel = GameViewModel.getGameViewModel();
        viewModel.configureDisplay(metrics);
        viewModel.setMap(map);

        viewModel.getTiles().observeForever(displayTiles -> {
            assertEquals(12, displayTiles.size());

            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;

            for (GraphicTile tile : displayTiles) {
                if (tile.getId() < min) {
                    min = tile.getId();
                }
                if (tile.getId() > max) {
                    max = tile.getId();
                }
            }

            assertEquals(5, min);
            assertEquals(16, max);
        });

        viewModel.setCameraCenter(10, 0);
    }
}
