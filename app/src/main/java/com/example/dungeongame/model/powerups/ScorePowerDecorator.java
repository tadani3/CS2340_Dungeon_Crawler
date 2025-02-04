package com.example.dungeongame.model.powerups;

import com.example.dungeongame.model.Player;
/**
 * A power decorator that will double the player's score.
 */
public class ScorePowerDecorator extends PowerDecorator {

    private PowerUp coin;

    /**
     * This is the essential constructor for the decorator that allows for the pattern to function
     * properly. It takes in a PowerUp instance that it will decorate and in our implementation it
     * is going to be an instance of the Player class.
     * @param coin the PowerUp passed in will most likely be the Player instance.
     */
    public  ScorePowerDecorator(PowerUp coin) {
        this.coin = coin;
    }

    /**
     * This is the functional method for the decorator that allows for the pattern to override the
     * concrete components gainPower(). It will expand upon the Player gainPower() implementation to
     * include the new ScorePowerDecorator's method functionality. It takes in a PowerUp
     * instance that will be used for distinguishing between powerTypes in the player class. Here
     * it's passed so that the Player class's gainPower() can properly set its activePowerNames
     * instance data.
     * @param powerType the PowerUp passed in is used for player data.
     */
    @Override
    public void gainPower(String powerType) {
        Player player = Player.getPlayer();
        // execute the players gain power method
        coin.gainPower("money");
        int currentscore = player.getScore();
        player.setScore(currentscore * 2);

    }
}
