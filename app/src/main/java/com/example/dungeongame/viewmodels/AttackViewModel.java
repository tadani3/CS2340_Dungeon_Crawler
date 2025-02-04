package com.example.dungeongame.viewmodels;

import com.example.dungeongame.model.GameMap;
import com.example.dungeongame.model.Player;
import com.example.dungeongame.model.weapons.Weapon;
import com.example.dungeongame.model.enemies.Enemy;

import com.example.dungeongame.views.DeathView;
import com.example.dungeongame.views.GameActivity;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class AttackViewModel {
  
    private static final Player PLAYER = Player.getPlayer();

    private GameActivity gameActivity;

    private Timer attackTimer;
    private Timer deathTimer;

    private volatile boolean attackCooldown;
    private volatile boolean delay;

    private static int[] deathBuffer;
  
    private Weapon weapon;

    /**
     * Initialize the attack view model
     * @param activity the main gameActivity
     */
    public AttackViewModel(GameActivity activity) {
        this.gameActivity = activity;
        attackTimer = new Timer();
        deathTimer = new Timer();
    }

    /**
     * Called when the player makes an attack. Goes through the sequence of methods required for
     * attack functionality.
     */
    public void playerAttacked() {
        if (!attackCooldown && !delay && PLAYER.getRightHand() instanceof Weapon) {
            Logger.getGlobal().info("Player attacked.");
            delay = true;
            weapon = (Weapon) PLAYER.getRightHand();
            checkCollision();
            delay = false;
        }
    }

    /**
     * Render and then hide the weapon when the player makes an attack. Also implements a cooldown,
     * so the player needs to wait briefly before attacking again.
     */
    private void weaponShowHide() {
        if (!gameActivity.renderWeapon()) {
            return;
        }
        TimerTask attackTask = new TimerTask() {
            @Override
            public void run() {
                gameActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gameActivity.unrenderWeapon();
                        attackCooldown = false;
                    }
                });
            }
        };
        attackCooldown = true;
        attackTimer.schedule(attackTask, 1000);
    }

    /**
     * Checks if the player's attack has collided with the enemy. Currently implemented so that the
     * enemy gets killed if they are on any of the tiles surrounding the player on the same tile as
     * the player.
     */
    private void checkCollision() {
        GameMap map = GameMap.getActiveMap();
        map.pauseSpawners();
        for (Object o : map.getEnemies().toArray()) {
            Enemy e = (Enemy) o;
            if (e.isActive() && weapon != null && weapon.getDurability() > 0
                    && weapon.inRange(PLAYER, e)) {
                Logger.getGlobal().info("Attacked " + e);
                weapon.attack(e);
                weaponShowHide();
                if (e.getCurrentHealth() <= 0) { // Check if the enemy is dead
                    killEnemy(e);
                }
            }
        }
        map.startSpawners();
    }

    /**
     * Performs the actions required to kill the enemy.
     * @param enemy the enemy to kill
     */
    private void killEnemy(Enemy enemy) {
        deathBuffer = new int[2];
        deathBuffer[0] = enemy.getX();
        deathBuffer[1] = enemy.getY();
        enemy.kill();

        // accumulating score
        PLAYER.addScore(enemy.getScore());

        DeathView deathView = gameActivity.renderDeath();
        TimerTask deathTask = new TimerTask() {
            @Override
            public void run() {
                gameActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gameActivity.unrenderDeath(deathView);
                    }
                });
            }
        };
        deathTimer.schedule(deathTask, 200);
    }

    /**
     * Used by the renderer to determine where to put the enemy's death effect.
     * @return the coordinates to place the effect
     */
    public static int[] getDeathBuffer() {
        return deathBuffer;
    }

}
