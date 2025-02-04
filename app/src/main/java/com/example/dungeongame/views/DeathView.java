package com.example.dungeongame.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

import com.example.dungeongame.R;
import com.example.dungeongame.model.Player;
import com.example.dungeongame.viewmodels.AttackViewModel;
import com.example.dungeongame.viewmodels.GameViewModel;


public class DeathView extends View {
    private Bitmap bitmap;
    private GameViewModel gameViewModel;

    public DeathView(Context context, GameViewModel gameViewModel) {
        super(context);

        bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                R.drawable.pixelsmoke), gameViewModel.getTileWidth(),
                gameViewModel.getTileWidth(), false);

        this.gameViewModel = gameViewModel;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Player player = Player.getPlayer();

        int[] coords = AttackViewModel.getDeathBuffer();

        // drawing the image
        canvas.drawBitmap(bitmap, gameViewModel.mapCoordsToScreenCoordsX(coords[0]),
                gameViewModel.mapCoordsToScreenCoordsY(coords[1]), null);
    }

}