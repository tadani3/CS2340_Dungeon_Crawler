package com.example.dungeongame.model;



import com.example.dungeongame.model.powerups.PowerUp;
import com.example.dungeongame.model.enemies.Locatable;
import com.example.dungeongame.viewmodels.observation.Observer;
import com.example.dungeongame.viewmodels.observation.Subject;
import com.example.dungeongame.viewmodels.strategies.MovementStrategy;
import com.example.dungeongame.viewmodels.strategies.Walking;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;


/**
 * Made it so that the player extends from PowerUp so that it can be decorated with powerUps
 */

public class Player extends PowerUp implements Locatable, Resettable, Subject {


    // VARIABLES
    // Player Basic Information
    private static Player player;
    private String playerName;
    private int spriteID;
    private int maxHP;
    private int currentHP;
    // Score
    private int score = 0;
    private Runnable scoreUpdateCallback;

    // Leaderboard
    private static final Leaderboard LEADERBOARD = Leaderboard.getLeaderboard();
    private LeaderboardEntry leaderboardEntry;

    // End Game
    private boolean victory = false;

    // Movement
    private float x;
    private float y;
    private MovementStrategy movementStrategy;

    // OBSERVER PATTERN
    private List<Observer> observers = new ArrayList<>();
    /**
     * an array that will help the power to "appear" once consumed. If not empty, it holds
     * the [health, teleport, attack, coin] in this order.
     */
    private String[] activePowerNames;

    // Inventory
    private Object rightHand; // What the player is currently holding in his/her right hand.

  
  
    // CONSTRUCTORS
    private Player() {
        this.x = 16;
        this.y = 16;
        this.movementStrategy = new Walking(); //Initialize movement strategy to walking
        activePowerNames = new String[4];
    }


  
    // METHODS
    /**
     * Method for getting the player.
     * PATTERN: SINGLETON
     *
     * @return Player object of the player
     */
    public static Player getPlayer() {
        if (player == null) {
            player = new Player();
        }
        return player;
    }



    /**
     * Resets all of the Player's variables for a new run.
     * @return True if the reset was completed successfully, else false
     */
    public boolean reset() {
        this.victory = false;
        Configuration.getConfig().updatePlayer();
        this.activePowerNames = new String[4];
        clearLeaderboardEntry();
        return true;
    }



    // PLAYER NAME METHODS
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Sets the playerName if the name is valid.
     *
     * @param playerName the name to set
     * @return the validity of the name and if it was set or not
     */
    public boolean setPlayerName(String playerName) {
        //Check for an empty string
        if (playerName == null || playerName.length() == 0) {
            return false;
        }
        //Check for a name that consists entirely of white space
        for (int i = 0; i < playerName.length(); i++) {
            if (playerName.charAt(i) != ' ') {
                this.playerName = playerName;
                return true;
            }
        }
        return false;
    }
  
  

    // PLAYER SPRITE METHODS
    public int getSpriteId() {
        return spriteID;
    }
  
    public void setSpriteId(int spriteID) {
        this.spriteID = spriteID;
    }
  
 
  
    // HEALTH METHODS
    public int getHP() {
        return currentHP;
    }
  
    public void setHP(int hp) {
        this.currentHP = hp;
    }

    /**
     * Decreases the player's health by the given amount and modifies the score accordingly.
     * @param damageAmt the amount of HP points to decrease by
     */
    public void takeDamage(int damageAmt) {
        addScore((int) (-0.5 * damageAmt));
        this.currentHP -= damageAmt;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }



    // SCORE METHODS
    /**
     * Reset the score to a given amount
     * @param resetAmount the score to reset to
     */
    public void resetScore(int resetAmount) {
        score = resetAmount;
        if (scoreUpdateCallback != null) {
            scoreUpdateCallback.run();
        }
    }

    /**
     * Set the callback to be run when the score is updated
     * @param callback the callback to be run
     */
    public void setScoreUpdateCallback(Runnable callback) {
        this.scoreUpdateCallback = callback;
    }

    /** Add points to the score
     * @param points the number of points to add to the score, may be negative
     */
    public void addScore(int points) {
        score += points;
        if (scoreUpdateCallback != null) {
            scoreUpdateCallback.run();
        }
    }
  
    public int getScore() {
        return score;
    }
  
    public void setScore(int score) {
        this.score = score;
    }



    // LOCATION METHODS
    public void setX(float x) {
        this.x = x;
        notifyObservers();
    }

    public void setY(float y) {
        this.y = y;
        notifyObservers();
    }

    public int getX() {
        return (int) (this.x + 0.5f);
    }

    public int getY() {
        return (int) (this.y + 0.5f);
    }

    /**
     * Gets the true floating point representation of location, even if visually
     * is still grid-aligned
     * @return the x component of location
     */
    public float getXTrue() {
        return this.x;
    }

    /**
     * Gets the true floating point representation of location, even if visually
     * is still grid-aligned
     * @return the y component of location
     */
    public float getYTrue() {
        return this.y;
    }



    // STRATEGY METHODS
    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }

    public MovementStrategy getMovementStrategy() {
        return movementStrategy;
    }



    // LEADERBOARD METHODS
    /**
     * Getter for the player's current leaderboard entry.
     *
     * @return LeaderboardEntry of the player's up-to-date leaderboard entry
     */
    public LeaderboardEntry getLeaderboardEntry() {
        if (leaderboardEntry == null) {
            leaderboardEntry = LEADERBOARD.addEntry(playerName, score); // CREATE ENTRY
        } else {
            leaderboardEntry.setScore(score);
            leaderboardEntry.setTimeStamp(Calendar.getInstance().getTime());
            LEADERBOARD.updateEntry(leaderboardEntry);
        }
        return leaderboardEntry;
    }

    /**
     * Clears the player's leaderboard entry if the Player's leaderboard entry has been finalized.
     *
     * @return Boolean true if the clearing of the leaderboard succeeds, false otherwise
     */
    public boolean clearLeaderboardEntry() {
        if (!Player.getPlayer().getLeaderboardEntry().isEditable()) {
            leaderboardEntry = null;
            return true;
        }
        return false;
    }

    /**
     * Getter for the player's victory status.
     *
     * @return boolean true if the player is victorious else false
     */
    public boolean isVictory() {
        return victory;
    }

    /**
     * Setter for the player's victory status.
     *
     * @param victory boolean true if the player is victorious else false
     */
    public void setVictory(boolean victory) {
        this.victory = victory;
    }



    // INVENTORY METHODS
    /**
     * Getter for the object in the player's right hand.
     * @return Object of what is in the player's right hand
     */
    public Object getRightHand() {
        return rightHand;
    }

    /**
     * Setter for the object in the player's right hand.
     * @param rightHand Object of what is in the player's right hand
     */
    public void setRightHand(Object rightHand) {
        this.rightHand = rightHand;
    }

    /**
     * gainPower() method for the player to gain a powerUp. Any use of the players gainPower() will
     * run this code. The 3 different types of Decorator override this method of same name and add
     * unique components that make the different powers unique.
     */
    @Override
    public void gainPower(String powerType) {
        switch (powerType) {
        case "health":
            activePowerNames[0] = powerType;
            break;
        case "teleport":
            activePowerNames[1] = powerType;
            break;
        case "attack":
            activePowerNames[2] = powerType;
            break;
        case "money":
            activePowerNames[3] = powerType;
            break;
        default:
            break;
        }
        // what does every power Do?
        // Im not really sure yet...
    }
    public String[] getActivePowerNames() {
        return activePowerNames;
    }
    public void setActivePowerName(int index, String name) {
        activePowerNames[index] = name;
    }





    // OBSERVER PATTERN METHODS
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

  


    // TEST CASE METHODS
    /**
     * METHOD THAT SHOULD ONLY BE CALLED IN UNIT TESTS
     */
    public void nullPlayer() {
        player = null;
        leaderboardEntry = null;
    }
  
    public void logPlayerPosition() {
        Logger.getGlobal().info(this.x + ", " + this.y);
    }

} // FIN
