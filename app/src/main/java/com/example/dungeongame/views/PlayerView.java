package com.example.dungeongame.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import com.example.dungeongame.model.Player;
import com.example.dungeongame.model.tileset.SpriteTileProvider;
import com.example.dungeongame.model.tileset.SpriteType;
import com.example.dungeongame.viewmodels.GameViewModel;


public class PlayerView extends View {
    private Bitmap bitmap;
    private GameViewModel gameViewModel;

    public PlayerView(Context context, GameViewModel gameViewModel) {
        super(context);

        bitmap = SpriteTileProvider.getInstance().getSpriteTile(
                SpriteType.SPRITE_PLAYER,
                gameViewModel,
                context);

        this.gameViewModel = gameViewModel;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Player player = Player.getPlayer();

        // drawing the image
        canvas.drawBitmap(bitmap, gameViewModel.mapCoordsToScreenCoordsX(player.getX()),
                gameViewModel.mapCoordsToScreenCoordsY(player.getY()), null);
    }

}