package com.example.dungeongame.model.tileset;

import com.example.dungeongame.model.GameMap;
import com.example.dungeongame.model.Player;

/**
 * Iron Bars (ID 5.5 = 85) currently represent the exit tiles.
 */
public class ExitTile extends MapTile {
    private GameMap nextMap;
    private int nextX;
    private int nextY;
    private boolean exploredBefore;

    public ExitTile(int x, int y, int id) {
        super(x, y, id);
        exploredBefore = false;
    }

    public void setNextInfo(GameMap nextMap, int nextX, int nextY) {
        this.nextMap = nextMap;
        this.nextX = nextX;
        this.nextY = nextY;
        this.exploredBefore = false; // setNextInfo is called on game reset, so exploration reset
    }

    public int getNextX() {
        return nextX;
    }

    public int getNextY() {
        return nextY;
    }

    public GameMap getNextMap() {
        if (!exploredBefore) {
            this.exploredBefore = true;
            Player.getPlayer().addScore(2); // 2 points for each exit tile discovered
        }
        return nextMap;
    }

}
