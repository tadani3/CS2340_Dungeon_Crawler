package com.example.dungeongame.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

import com.example.dungeongame.model.enemies.Enemy;
import com.example.dungeongame.model.tileset.SpriteTileProvider;
import com.example.dungeongame.model.tileset.SpriteType;
import com.example.dungeongame.viewmodels.GameViewModel;

public class EnemyView extends View {
    private Bitmap bitmap;
    private GameViewModel gameViewModel;
    private Enemy enemy;

    public EnemyView(Context context, GameViewModel gameViewModel, Enemy enemy) {
        super(context);

        SpriteType spriteType;
        switch (enemy.getType()) {
        case "MichelinGhost":
            spriteType = SpriteType.SPRITE_ENEMY_GHOST_MICHELIN;
            break;
        case "PhantomGhost":
            spriteType = SpriteType.SPRITE_ENEMY_GHOST_PHANTOM;
            break;
        case "OgreGoblin":
            spriteType = SpriteType.SPRITE_ENEMY_GOBLIN_OGRE;
            break;
        case "OrcGoblin":
        default:
            spriteType = SpriteType.SPRITE_ENEMY_GOBLIN_ORC;
            break;
        }

        bitmap = SpriteTileProvider.getInstance().getSpriteTile(spriteType, gameViewModel, context);

        this.gameViewModel = gameViewModel;
        this.enemy = enemy;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // drawing the image
        canvas.drawBitmap(bitmap, gameViewModel.mapCoordsToScreenCoordsX(enemy.getX()),
                gameViewModel.mapCoordsToScreenCoordsY(enemy.getY()), null);
    }
}
