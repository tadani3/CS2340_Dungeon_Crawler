package com.example.dungeongame.model.powerups;

import com.example.dungeongame.model.Configuration;
import com.example.dungeongame.model.Player;

/**
 * A power decorator that will allow the player to gain more health points.
 */
public class HealthPowerDecorator extends PowerDecorator {
    private PowerUp healthPower;

    /**
     * This is the essential constructor for the decorator that allows for the pattern to function
     * properly. It takes in a PowerUp instance that it will decorate and in our implementation it
     * is going to be an instance of the Player class.
     * @param healthPower the PowerUp passed in will most likely be the Player instance.
     */
    public HealthPowerDecorator(PowerUp healthPower) {
        this.healthPower = healthPower;
    }

    /**
     * This is the functional method for the decorator that allows for the pattern to override the
     * concrete components gainPower(). It will expand upon the Player gainPower() implementation to
     * include the new HealthPowerDecorator's method functionality. It takes in a PowerUp instance
     * that will be used for distinguishing between powerTypes in the player class. Here it's passed
     * so that the Player class's gainPower() can properly set its activePowerNames instance data.
     * @param powerType the PowerUp passed in is used for player data.
     */
    @Override
    public void gainPower(String powerType) {
        Player player = Player.getPlayer();
        int diff = Configuration.getConfig().getDifficulty();
        // execute the players gain power method
        healthPower.gainPower(powerType);
        //added component due to health power decorator
        switch (diff) {
        case 1:
            player.setHP(200); //setStartingHP
            break;
        case 2:
            player.setHP(150); //setStartingHP
            break;
        case 3:
            player.setHP(100); //setStartingHP
            break;
        default:
            break;
        }
    }
}
