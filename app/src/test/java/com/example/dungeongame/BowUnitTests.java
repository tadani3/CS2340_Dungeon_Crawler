package com.example.dungeongame;
import com.example.dungeongame.model.GameMap;
import com.example.dungeongame.model.Player;
import com.example.dungeongame.model.weapons.Bow;
import com.example.dungeongame.model.weapons.Sword;
import com.example.dungeongame.model.enemies.Enemy;
import com.example.dungeongame.model.enemies.PhantomGhost;
import com.example.dungeongame.model.tileset.MapTile;
import com.example.dungeongame.model.weapons.Weapon;

import org.junit.Before;
import org.junit.Test;

public class BowUnitTests {
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
        player.setX(3);
        player.setY(3);
        player.setRightHand(new Bow());
        playerWeapon = (Weapon) player.getRightHand();
    }

    /**
     * Test for proper initialization of Bow weapon.
     */
    @Test
    public void testBowInitialization() {
        Bow modelBow = new Bow();
        assert (playerWeapon.getDurability() == modelBow.getDurability());
        assert (playerWeapon.getAttackPower() == modelBow.getAttackPower());
        assert (playerWeapon.getWeight() == modelBow.getWeight());
    }

    /**
     * Test to verify that the Player's Bow correctly attacks an enemy upon collision.
     */
    @Test
    public void testAttackEnemy() {
        Bow playerBow = (Bow) playerWeapon;
        playerBow.load(10);
        playerBow.setHasQuiver(true);
        int oldDurability = playerBow.getDurability();
        Object[] drops = new Object[0];
        Enemy e = new PhantomGhost(drops, map, 8, 4);
        playerBow.prepare(e);
        playerBow.attack(e);
        assert (playerWeapon.getDurability() == oldDurability - 1);
    }

    /**
     * Test to ensure the attack() method does not execute if the attacked object is not an
     * instance of an enemy.
     */
    @Test
    public void testAttackNoEnemy() {
        int oldDurability = playerWeapon.getDurability();
        MapTile tile = map.getTileAt(2, 2);
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
        Enemy e = new PhantomGhost(drops, map, 5, 5);
        int enemyHealth = e.getCurrentHealth();
        playerWeapon.attack(e);
        assert (e.getCurrentHealth() == enemyHealth);
        assert (playerWeapon.getDurability() == 0);
    }

    /**
     * Test to ensure that the player cannot attack an out-of-range enemy.
     */
    @Test
    public void testBowOutOfRange() {
        Object[] drops = new Object[0];
        Enemy e = new PhantomGhost(drops, map, 10, 10);
        int oldDurability = playerWeapon.getDurability();
        int enemyHealth = e.getCurrentHealth();
        if (playerWeapon.inRange(player, e)) {
            playerWeapon.attack(e);
        }
        assert (e.getCurrentHealth() == enemyHealth);
        assert (playerWeapon.getDurability() == oldDurability);
    }

    /**
     * Test to ensure the Weapon class repair() method logic works correctly.
     */
    @Test
    public void testBowRepair() {
        int oldDurability = playerWeapon.getDurability();
        int repairAmount = 50;
        assert (playerWeapon.repair(repairAmount));
        assert (playerWeapon.getDurability() == oldDurability + repairAmount);
    }
}
