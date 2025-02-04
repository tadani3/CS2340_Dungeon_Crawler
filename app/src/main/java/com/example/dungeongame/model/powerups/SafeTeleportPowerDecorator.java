package com.example.dungeongame.model.powerups;

import com.example.dungeongame.model.Player;
/**
 * A power decorator that will allow the player to safely teleport away to a safe location.
 */
public class SafeTeleportPowerDecorator extends PowerDecorator {

    private PowerUp teleport;

    /**
     * This is the essential constructor for the decorator that allows for the pattern to function
     * properly. It takes in a PowerUp instance that it will decorate and in our implementation it
     * is going to be an instance of the Player class.
     * @param teleport the PowerUp passed in will most likely be the Player instance.
     */
    public SafeTeleportPowerDecorator(PowerUp teleport) {
        this.teleport = teleport;
    }

    /**
     * This is the functional method for the decorator that allows for the pattern to override the
     * concrete components gainPower(). It will expand upon the Player gainPower() implementation to
     * include the new SafeTeleportPowerDecorator's method functionality. It takes in a PowerUp
     * instance that will be used for distinguishing between powerTypes in the player class. Here
     * it's passed so that the Player class's gainPower() can properly set its activePowerNames
     * instance data.
     * @param powerType the PowerUp passed in is used for player data.
     */
    @Override
    public void gainPower(String powerType) {
        Player player = Player.getPlayer();
        // execute the players gain power method
        teleport.gainPower(powerType);
        // can rework in future PR
        player.setX(12);
        player.setY(12);
    }
}
