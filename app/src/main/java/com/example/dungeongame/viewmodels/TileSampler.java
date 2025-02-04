package com.example.dungeongame.viewmodels;

import android.graphics.Bitmap;

public class TileSampler {
    private static TileSampler instance;
    private Bitmap[] tileBitmaps;

    private TileSampler() {

    }

    public static TileSampler getInstance() {
        if (instance == null) {
            instance = new TileSampler();
        }
        return instance;
    }

    public void setTileset(Bitmap allTiles, int numX, int numY, int tileSize) {
        this.tileBitmaps = new Bitmap[numX * numY];

        int tileWidth = allTiles.getWidth() / numX;
        int tileHeight = allTiles.getHeight() / numY;

        for (int i = 0; i < numX; i++) {
            for (int j = 0; j < numY; j++) {
                this.tileBitmaps[i + j * numX] = Bitmap.createScaledBitmap(
                        Bitmap.createBitmap(
                                allTiles, i * tileWidth,
                                j * tileHeight, tileWidth, tileHeight),
                        tileSize, tileSize, false);
            }
        }
    }

    public Bitmap getTileBitmap(int id) {
        if (id < 0 || id >= this.tileBitmaps.length) {
            return null;
        }

        return this.tileBitmaps[id];
    }
}
