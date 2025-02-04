package com.example.dungeongame.model.tileset;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.dungeongame.R;
import com.example.dungeongame.model.Player;
import com.example.dungeongame.viewmodels.GameViewModel;

/**
 * A singleton class that provides bitmaps for the sprites so that they can be reused over instances
 */
public class SpriteTileProvider {
    private static SpriteTileProvider instance = null;
    private Bitmap[] bitmaps;

    private SpriteTileProvider() {
        bitmaps = new Bitmap[SpriteType.values().length];
        bitmaps[SpriteType.SPRITE_PLAYER.ordinal()] = null;
        bitmaps[SpriteType.SPRITE_ENEMY_GHOST_MICHELIN.ordinal()] = null;
        bitmaps[SpriteType.SPRITE_ENEMY_GHOST_PHANTOM.ordinal()] = null;
        bitmaps[SpriteType.SPRITE_ENEMY_GOBLIN_OGRE.ordinal()] = null;
        bitmaps[SpriteType.SPRITE_ENEMY_GOBLIN_ORC.ordinal()] = null;
        // new power up sprites
        bitmaps[SpriteType.SPRITE_ITEM_HEALTH.ordinal()] = null;
        bitmaps[SpriteType.SPRITE_ITEM_ATTACK.ordinal()] = null;
        bitmaps[SpriteType.SPRITE_ITEM_TELEPORT.ordinal()] = null;
        bitmaps[SpriteType.SPRITE_ITEM_SCORE.ordinal()] = null;

    }

    public static SpriteTileProvider getInstance() {
        if (instance == null) {
            instance = new SpriteTileProvider();
        }

        return instance;
    }

    public void clearPlayerCache() {
        bitmaps[SpriteType.SPRITE_PLAYER.ordinal()] = null;
    }

    public Bitmap getSpriteTile(SpriteType spriteType, GameViewModel gameViewModel, Context ctx) {
        int index = spriteType.ordinal();
        if (bitmaps[index] == null) {
            int resource;
            switch (spriteType) {
            case SPRITE_ENEMY_GHOST_MICHELIN:
                resource = R.drawable.staypuft;
                break;
            case SPRITE_ENEMY_GHOST_PHANTOM:
                resource = R.drawable.ghost;
                break;
            case SPRITE_ENEMY_GOBLIN_OGRE:
                resource = R.drawable.ogre_walking_000;
                break;
            case SPRITE_ENEMY_GOBLIN_ORC:
                resource = R.drawable.orc_walking_000;
                break;
            case SPRITE_ITEM_HEALTH:
                resource = R.drawable.heartpower;
                break;
            case SPRITE_ITEM_TELEPORT:
                resource = R.drawable.teleportpower;
                break;
            case SPRITE_ITEM_ATTACK:
                resource = R.drawable.attackpower;
                break;
            case SPRITE_ITEM_SCORE:
                resource = R.drawable.level_up;
                break;
            case SPRITE_PLAYER:
            default:
                int playerSpriteId = Player.getPlayer().getSpriteId();
                switch (playerSpriteId) {
                case 2:
                    resource = R.drawable.spriteplayer2;
                    break;
                case 3:
                    resource = R.drawable.spriteplayer3;
                    break;
                case 1:
                default:
                    resource = R.drawable.spriteplayer1;
                    break;
                }
                break;
            }

            bitmaps[index] = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(ctx.getResources(), resource),
                    gameViewModel.getTileWidth(),
                    gameViewModel.getTileWidth(), false);
        }

        return bitmaps[index];
    }
}
