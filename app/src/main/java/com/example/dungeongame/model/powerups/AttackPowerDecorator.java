package com.example.dungeongame.model.powerups;


import com.example.dungeongame.model.Clock;
import com.example.dungeongame.model.GameMap;
import com.example.dungeongame.model.Player;

/**
 * A concrete power decorator that will help deliver the ultimate attack killing all enemies.
 */
public class AttackPowerDecorator extends PowerDecorator {
    private PowerUp instantKill;
    private static final Clock CLOCK = Clock.getClock();
  
    /**
     * This is the essential constructor for the decorator that allows for the pattern to function
     * properly. It takes in a PowerUp instance that it will decorate and in our implementation it
     * is going to be an instance of the Player class.
     * @param instantKill the PowerUp passed in will most likely be the Player instance.
     */
    public AttackPowerDecorator(PowerUp instantKill) {
        this.instantKill = instantKill;
    }
    /**
     * This is the functional method for the decorator that allows for the pattern to override the
     * concrete components gainPower(). It will expand upon the Player gainPower() implementation to
     * include the new AttackPowerDecorator's method functionality. It takes in a PowerUp instance
     * that will be used for distinguishing between powerTypes in the player class. Here it's passed
     * so that the Player class's gainPower() can properly set its activePowerNames instance data.
     * @param powerType the PowerUp passed in is used for player data.
     */
    @Override
    public void gainPower(String powerType) {
        Player player = Player.getPlayer();
        // execute the players gain power method
        instantKill.gainPower(powerType);
        CLOCK.pause();
        GameMap map = GameMap.getActiveMap();
        map.clearEnemies();
        CLOCK.start();
    }
}
