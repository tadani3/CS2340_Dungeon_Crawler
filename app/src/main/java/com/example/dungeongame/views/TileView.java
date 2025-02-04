package com.example.dungeongame.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import com.example.dungeongame.viewmodels.GraphicTile;
import com.example.dungeongame.viewmodels.TileSampler;

public class TileView extends View {
    private GraphicTile tile;

    public TileView(Context context, GraphicTile tile) {
        super(context);

        this.tile = tile;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Bitmap tileImage = TileSampler.getInstance().getTileBitmap(this.tile.getId());
        if (tileImage == null) {
            return;
        }

        // drawing the image
        canvas.drawBitmap(tileImage,
                this.tile.getX(), this.tile.getY(), null);
    }
}
