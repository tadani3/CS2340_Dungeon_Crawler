package com.example.dungeongame;
import com.example.dungeongame.model.Player;
import com.example.dungeongame.model.tileset.MapTile;
import com.example.dungeongame.viewmodels.GameViewModel;
import com.example.dungeongame.viewmodels.PlayerViewModel;
import com.example.dungeongame.views.GameActivity;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * JUnit tests created for the Tile classes. The testTileCreation() unit test was created by
 * Tommaso Adani.
 *
 * @author Tommaso Adani
 * @version 1.0.0
 */
public class TileCreationUnitTests {

    private MapTile tile = new MapTile(1, 1, 5);

    @Test
    public void testTileCreation() {
        assert (tile.getX() == 1);
        assert (tile.getY() == 1);
        assert (tile.getId() == 5);
    }
    /**
     * Connor Smith Sprint 4 Junit Test #2
     */
    @Test
    public void testGetIdString() {
        int x = tile.getId();
        String test = String.valueOf(x);
        assertEquals(tile.toString(), test);
    }

}