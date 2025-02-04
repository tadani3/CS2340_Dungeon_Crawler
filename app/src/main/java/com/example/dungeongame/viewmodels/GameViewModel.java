package com.example.dungeongame.viewmodels;

import android.util.DisplayMetrics;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dungeongame.model.GameMap;
import com.example.dungeongame.model.tileset.MapTile;
import com.example.dungeongame.model.Player;

import java.util.ArrayList;

public class GameViewModel extends PlayerAttemptViewModel {
    private float cameraCenterX;
    private float cameraCenterY;
    private DisplayMetrics displayMetrics;
    private float tilesInCameraHeight;

    private static final int TILES_IN_CAMERA_WIDTH = 10;
    private static final int TILES_IN_MARGIN = 0;
    private static final float MOVEMENT_RATE = 4f; // measured in tiles per second
    private final MutableLiveData<ArrayList<GraphicTile>> tiles;
    private final MutableLiveData<ArrayList<Float>> joystickPosition;

    private GameMap map;

    private static GameViewModel instance;

    private GameViewModel() {
        this.cameraCenterX = 0;
        this.cameraCenterY = 0;

        this.tiles = new MutableLiveData<>();
        this.joystickPosition = new MutableLiveData<>();
    }

    public static GameViewModel getGameViewModel() {
        if (instance == null) {
            instance = new GameViewModel();
        }
        return instance;
    }

    public void configureDisplay(DisplayMetrics displayMetrics) {
        // getting screen height to width ratio
        this.displayMetrics = displayMetrics;
        int screenHeight = this.displayMetrics.heightPixels;
        int screenWidth = this.displayMetrics.widthPixels;
        float ratio = (float) screenHeight / (float) screenWidth;

        // getting the number of vertical tiles to display
        this.tilesInCameraHeight = TILES_IN_CAMERA_WIDTH * ratio;
    }

    public void recenterCamera() {
        Player player = Player.getPlayer();
        setCameraCenter(player.getX(), player.getY());
    }

    public void setCameraCenter(float x, float y) {
        this.cameraCenterX = x;
        this.cameraCenterY = y;

        // updating tiles visible
        float effCamX = this.getEffectiveCamX();
        float effCamY = this.getEffectiveCamY();

        // getting the tiles to display
        int startMapX = (int) (effCamX - (float) TILES_IN_CAMERA_WIDTH / 2);

        int startMapY = (int) (effCamY - tilesInCameraHeight / 2);

        int endMapX = (int) (effCamX + (float) TILES_IN_CAMERA_WIDTH / 2) + 1;

        int endMapY = (int) (effCamY + tilesInCameraHeight / 2) + 1;

        ArrayList<GraphicTile> tiles = new ArrayList<>();
        for (int mapY = startMapY; endMapY >= mapY; mapY++) {
            for (int mapX = startMapX; endMapX >= mapX; mapX++) {
                MapTile tile = this.map.getTileAt(mapX, mapY);
                if (tile == null) {
                    continue;
                }

                int tileScreenX = this.mapCoordsToScreenCoordsX(mapX);

                int tileScreenY = this.mapCoordsToScreenCoordsY(mapY);

                GraphicTile graphicTile = new GraphicTile(tile, tileScreenX, tileScreenY);
                tiles.add(graphicTile);
            }
        }

        this.tiles.setValue(tiles);
    }

    public int getTileWidth() {
        return this.displayMetrics.widthPixels / TILES_IN_CAMERA_WIDTH;
    }


    /**
     * Sets the map to be rendered. Update will not take effect until next call to setCameraCenter
     * @param map the map to be rendered, must not be null
     */
    public void setMap(GameMap map) {
        this.map = map;
        GameMap.setActiveMap(map);
    }

    /**
     * Returns the map that is currently being rendered
     * @return the map that is currently being rendered
     */
    public GameMap getMap() {
        return this.map;
    }

    /**
     * Access to the live data of the tiles to be rendered
     * Only tiles that are visible will be returned and will be graphic tiles with
     * screen coordinates.
     * @return the live data of the tiles to be rendered
     */
    public LiveData<ArrayList<GraphicTile>> getTiles() {
        return this.tiles;
    }

    public LiveData<ArrayList<Float>> getJoystickPosition() {
        return this.joystickPosition;
    }

    // helper methods

    /**
     * Converts a map x coordinate to a screen x coordinate
     * @param mapX the map x coordinate
     * @return the screen x coordinate, for the top left corner of the tile
     */
    public int mapCoordsToScreenCoordsX(float mapX) {
        float effCamX = this.getEffectiveCamX();

        return (int) ((mapX - effCamX + (float) TILES_IN_CAMERA_WIDTH / 2)
                * this.displayMetrics.widthPixels / TILES_IN_CAMERA_WIDTH);
    }

    /**
     * Converts a map y coordinate to a screen y coordinate
     * @param mapY the map y coordinate
     * @return the screen y coordinate, for the top left corner of the tile
     */
    public int mapCoordsToScreenCoordsY(float mapY) {
        float effCamY = this.getEffectiveCamY();

        return (int) ((mapY - effCamY + tilesInCameraHeight / 2)
                * this.displayMetrics.heightPixels / tilesInCameraHeight);
    }

    /**
     * Returns the camera x coordinate, which may be different from the set camera x coordinate
     * This is because the camera cannot go far past the edge of the map, so it gets constrained.
     * @return the effective camera x coordinate
     */
    private float getEffectiveCamX() {
        float effCamX = this.cameraCenterX;

        if (effCamX < (float) TILES_IN_CAMERA_WIDTH / 2 - TILES_IN_MARGIN) {
            effCamX = (float) TILES_IN_CAMERA_WIDTH / 2 - TILES_IN_MARGIN;
        } else if (effCamX > this.map.getWidth() - (float) TILES_IN_CAMERA_WIDTH / 2
                + TILES_IN_MARGIN) {
            effCamX = this.map.getWidth() - (float) TILES_IN_CAMERA_WIDTH / 2 + TILES_IN_MARGIN;
        }

        return effCamX;
    }

    /**
     * Returns the camera y coordinate, which may be different from the set camera y coordinate
     * This is because the camera cannot go far past the edge of the map, so it gets constrained.
     * @return the effective camera y coordinate
     */
    private float getEffectiveCamY() {
        float effCamY = this.cameraCenterY;

        if (effCamY < tilesInCameraHeight / 2 - TILES_IN_MARGIN) {
            effCamY = tilesInCameraHeight / 2 - TILES_IN_MARGIN;
        } else if (effCamY > this.map.getHeight() - tilesInCameraHeight / 2 + TILES_IN_MARGIN) {
            effCamY = this.map.getHeight() - tilesInCameraHeight / 2 + TILES_IN_MARGIN;
        }

        return effCamY;
    }

    /**
     * Communicates the joystick position to the view model, allowing it to process how the player
     * should move.
     * @param x the x position of the joystick, between -1 and 1
     * @param y the y position of the joystick, between -1 and 1
     * @param dt the time since the last update, in seconds
     */
    public void setJoystickPosition(float x, float y, float dt) {
        // bounding the joystick positions
        if (x < -1) {
            x = -1;
        } else if (x > 1) {
            x = 1;
        }

        if (y < -1) {
            y = -1;
        } else if (y > 1) {
            y = 1;
        }

        // For anything using the joystick position, it will be in the form of an array list
        // for example, the game activity uses this information to display where the joystick
        // is. The first element is the x position, the second is the y position, and the third
        // is the time since the last update.
        ArrayList<Float> position = new ArrayList<>();
        position.add(x);
        position.add(y);
        position.add(dt);
        this.joystickPosition.setValue(position);

        // Now updating the player. The player should be grid-aligned, but the joystick should allow
        // for different speeds of movement in each axis. To accomplish this, the player has a
        // 'true' position and a rendered position. The rendered position is grid-aligned, but the
        // true position is not. The true position is updated by the joystick, and the rendered
        // position is updated by the player's movement strategy.
        Player player = Player.getPlayer();
        float newX = player.getXTrue() + x * MOVEMENT_RATE * dt;
        float newY = player.getYTrue() + y * MOVEMENT_RATE * dt;
        if ((int) (newX + 0.5f) < player.getX()) {
            // moved left
            player.getMovementStrategy().moveLeft();
            PlayerViewModel.getPlayerViewModel().evaluateTile();
        } else if ((int) (newX + 0.5f) > player.getX()) {
            // moved right
            player.getMovementStrategy().moveRight();
            PlayerViewModel.getPlayerViewModel().evaluateTile();
        } else {
            player.setX(newX);
        }

        if ((int) (newY + 0.5f) < player.getY()) {
            // moved up
            player.getMovementStrategy().moveUp();
            PlayerViewModel.getPlayerViewModel().evaluateTile();
        } else if ((int) (newY + 0.5f) > player.getY()) {
            // moved down
            player.getMovementStrategy().moveDown();
            PlayerViewModel.getPlayerViewModel().evaluateTile();
        } else {
            player.setY(newY);
        }
    }
}
