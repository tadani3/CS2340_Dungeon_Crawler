package com.example.dungeongame.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;


import com.example.dungeongame.model.powerups.PowerUpItem;
import com.example.dungeongame.model.tileset.SpriteTileProvider;
import com.example.dungeongame.model.tileset.SpriteType;
import com.example.dungeongame.viewmodels.GameViewModel;

/**
 * PowerItemView class that is used to distinguish PowerUpItem views from enemies, and other
 * relevant or related components.
 */
public class PowerItemView extends View {
    private Bitmap bitmap;
    private GameViewModel gameViewModel;
    private PowerUpItem item;

    public PowerItemView(Context context, GameViewModel gameViewModel, PowerUpItem item) {
        super(context);

        SpriteType spriteType;
        switch (item.getPowerName()) {
        case "health":
            spriteType = SpriteType.SPRITE_ITEM_HEALTH;
            break;
        case "teleport":
            spriteType = SpriteType.SPRITE_ITEM_TELEPORT;
            break;
        case "attack":
            spriteType = SpriteType.SPRITE_ITEM_ATTACK;
            break;
        case "money":
            spriteType = SpriteType.SPRITE_ITEM_SCORE;
            break;
        default:
            //testing for noticeable difference
            spriteType = SpriteType.SPRITE_ENEMY_GHOST_MICHELIN;
            break;
        }

        bitmap = SpriteTileProvider.getInstance().getSpriteTile(spriteType, gameViewModel, context);

        this.gameViewModel = gameViewModel;
        this.item = item;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // drawing the image
        canvas.drawBitmap(bitmap, gameViewModel.mapCoordsToScreenCoordsX(item.getX()),
                gameViewModel.mapCoordsToScreenCoordsY(item.getY()), null);
    }
}
