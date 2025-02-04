package com.example.dungeongame;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.dungeongame.model.GameMap;
import com.example.dungeongame.model.Player;
import com.example.dungeongame.model.weapons.Bow;
import com.example.dungeongame.model.weapons.Sword;
import com.example.dungeongame.model.weapons.Weapon;
import com.example.dungeongame.model.enemies.Enemy;
import com.example.dungeongame.model.enemies.OrcGoblin;
import com.example.dungeongame.model.enemies.PhantomGhost;
import com.example.dungeongame.model.tileset.MapTile;

import org.junit.Before;
import org.junit.Test;

public class SwordUnitTests {
    Player player = Player.getPlayer();
    Weapon playerWeapon;
    GameMap map;

    /**
     * Sets Up a Map to check if collisions and attacks are being performed.
     */
    @Before
    public void setUp() {
        MapTile[][] tiles = new MapTile[10][10];
        tiles[0][0] = new MapTile(0, 0, 0);
        tiles[0][1] = new MapTile(1, 0, 1);
        tiles[0][2] = new MapTile(2, 0, 3);
        tiles[1][0] = new MapTile(0, 1, 4);
        map = new GameMap(tiles);
        player.setX(5);
        player.setY(5);
        player.setRightHand(new Sword());
        playerWeapon = (Weapon) player.getRightHand();
    }

    /**
     * Test to ensure that a Sword weapon and all of its attributes are initialized correctly.
     */
    @Test
    public void testSwordInitialization() {
        Sword modelSword = new Sword();
        assert (playerWeapon.getDurability() == modelSword.getDurability());
        assert (playerWeapon.getAttackPower() == modelSword.getAttackPower());
        assert (playerWeapon.getWeight() == modelSword.getWeight());
    }

    /**
     * Test to verify that the Player's Sword correctly attacks an enemy upon collision.
     */
    @Test
    public void testAttackEnemy() {
        int oldDurability = playerWeapon.getDurability();
        Object[] drops = new Object[0];
        Enemy e = new OrcGoblin(drops, map, 5, 5);
        int enemyHealth = e.getCurrentHealth();
        assert (playerWeapon.attack(e) == true);
        assert (e.getCurrentHealth() == enemyHealth - playerWeapon.getAttackPower());
        assert (playerWeapon.getDurability() == oldDurability - 1);
    }

    /**
     * Test to ensure the attack() method does not execute if the attacked object is not an
     * instance of an enemy.
     */
    @Test
    public void testAttackNoEnemy() {
        int oldDurability = playerWeapon.getDurability();
        MapTile tile = map.getTileAt(5, 4);
        assert (!(playerWeapon.attack(tile)));
        assert (playerWeapon.getDurability() == oldDurability);
    }

    /**
     * Test to ensure the attack() method does not execute if the weapon durability is less than 0.
     */
    @Test
    public void testAttackNoDurability() {
        playerWeapon.setDurability(0);
        Object[] drops = new Object[0];
        Enemy e = new OrcGoblin(drops, map, player.getX() + 1, player.getY() + 1);
        int enemyHealth = e.getCurrentHealth();
        playerWeapon.attack(e);
        assert (e.getCurrentHealth() == enemyHealth);
        assert (playerWeapon.getDurability() == 0);
    }

    /**
     * Test to ensure the Weapon class' repair() method logic works correctly.
     */
    @Test
    public void testSwordRepair() {
        int oldDurability = playerWeapon.getDurability();
        int repairAmount = 10;
        assert (playerWeapon.repair(repairAmount));
        assert (playerWeapon.getDurability() == oldDurability + repairAmount);
    }

    /**
     * Test to ensure that an enemy is removed from the map's list of enemies if killed by a
     * sword.
     */
    @Test
    public void testWeaponEnemyKill() {
        Object[] drops = new Object[0];
        Enemy e = new OrcGoblin(1, 2, drops, map, 5, 5);
        playerWeapon.attack(e);
        int enemyHealth = e.getCurrentHealth();
        assert (playerWeapon.attack(e) == true);
        assert (e.getCurrentHealth() == enemyHealth - playerWeapon.getAttackPower());
    }

    @Test
    public void testRange() {
        Object[] drops = new Object[0];
        Enemy e = new OrcGoblin(1, 2, drops, map, player.getX() + 3,
                player.getY());
        playerWeapon.setRange(4);
        assertTrue(playerWeapon.inRange(player, e));
        playerWeapon.setRange(2);
        assertFalse(playerWeapon.inRange(player, e));
    }
}
