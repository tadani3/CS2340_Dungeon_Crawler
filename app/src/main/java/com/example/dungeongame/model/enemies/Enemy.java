package com.example.dungeongame.model.enemies;

import com.example.dungeongame.model.Configuration;
import com.example.dungeongame.model.GameMap;
import com.example.dungeongame.model.Player;
import com.example.dungeongame.model.Util;
import com.example.dungeongame.viewmodels.observation.CollisionObserver;
import com.example.dungeongame.viewmodels.observation.Observer;
import com.example.dungeongame.viewmodels.observation.Subject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Basic abstract class that details an enemy. An enemy is defined by its name, id, type, max
 * health, current health, basic damage dealt on attack, movement strategy, and drops.
 * ---
 * What is the Factory Design Pattern? The Factory Method Design Pattern is a creational pattern
 * that provides an interface for creating objects but allows subclasses to decide which class to
 * instantiate.
 * ---
 * Pattern: Factory Design Pattern
 * @author Ethan Nguyen-Tu
 * @version 1.0.0
 */
public abstract class Enemy implements Subject, Locatable {

    // Basic Variables
    private String name;

    private int id;
    private int spriteID;
    private String type;

    // Combat Variables
    private int maxHealth;
    private int currentHealth;
    private int basicDamage;
    private AutomatedMovementStrategy movementStrategy;

    // Logistical Variables
    private Object[] drops;
    private final Random random = new Random();
    private static int count = 0;

    private int x;
    private int y;
    private GameMap map;
    private boolean isDead = false;

    private List<Observer> observers = new ArrayList<>();

    // COMBAT METHODS
    /**
     * Method that reduces the enemy's HP by the specified int amount.
     *
     * @param damage int of the damage that the enemy receives
     * @return int of the new current health of the enemy
     */
    public int takeDamage(int damage) {
        this.currentHealth -= damage;
        return this.currentHealth;
    }

    /**
     * Method that returns the a random object in the enemy drops list.
     * ---
     * USE CASE: Upon enemy death, drop an item for the player.
     * @return Object that the enemy drops
     */
    public Object dropItem() {
        return drops[(int) (random.nextDouble() * drops.length) / 2];
    }

    // INFORMATION METHODS
    /**
     * Override of the toString method that prints out all of the enemy abstract class basic
     * information.
     *
     * @return String detailing enemy name, type, current health, total health,and movement strategy
     */
    public String toString() {
        return name + " is of type " + type + " and has health of " + currentHealth
                + " remaining out of " + maxHealth + ". It moves around with " + movementStrategy
                + ".";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Enemy)) {
            return false;
        }
        Enemy enemy = (Enemy) o;
        return getId() == enemy.getId() && getMaxHealth() == enemy.getMaxHealth()
                && getCurrentHealth() == enemy.getCurrentHealth()
                && getBasicDamage() == enemy.getBasicDamage()
                && getName().equals(enemy.getName()) && getType().equals(enemy.getType())
                && getMovementStrategy().equals(enemy.getMovementStrategy())
                && Arrays.equals(getDrops(), enemy.getDrops())
                && Objects.equals(random, enemy.random);
    }

    /**
     * Auto-generated hash code feature depending on the enemy's name, id, type, max health, current
     * health, basic damage, and movement strategy.
     *
     * @return int of the enemy's hash code.
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(getName(), getId(), getType(), getMaxHealth(), getCurrentHealth(),
                getBasicDamage(), getMovementStrategy());
        result = 31 * result + Arrays.hashCode(getDrops());
        return result;
    }

    /**
     * Sets all of the basic parameters of the enemy.
     *
     * @param type String name of the manufactured enemy
     * @param maxHealth int maximum health of the manufactured enemy
     * @param basicDamage int basic damage dealt on attack of the manufactured enemy
     * @param drops Object[] of the drops of the manufactured enemy
     * @param map GameMap that the enemy will be created on
     * @param x int of the x starting coordinate of the enemy
     * @param y int of the y starting coordinate of the enemy
     */
    public Enemy(String type, int maxHealth, int basicDamage, Object[] drops,
                 GameMap map, int x, int y) {
        this.name = type + count;
        this.id = count;
        this.type = type;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.basicDamage = basicDamage;
        this.drops = drops;
        this.map = map;
        this.x = x;
        this.y = y;

        if (map != null && map.getEnemies().size() > 10) {
            // maps can only have 10 enemies displayed at a time
            this.isDead = true;
            return;
        }

        this.addObserver(CollisionObserver.getColObserver());
        if (map != null) {
            map.addEnemy(this);
        }
    }

    public void kill() {
        map.removeEnemy(this);
        this.isDead = true;

        if (this.getMovementStrategy() != null) {
            this.getMovementStrategy().finalizeTicks();
        }
    }

    /**
     * Method checks if player has collided with an enemy and reduces the player's HP by the
     * appropriate amount.
     */
    public void checkCollision() {
        if (!this.isActive()) {
            return;
        }

        Player p = Player.getPlayer();
        Configuration c = Configuration.getConfig();

        if (Util.inRange(p, this, 2)) {
            int damage;
            switch (c.getDifficulty()) {
            case 2:
                damage = basicDamage;
                break;
            case 3:
                damage = (basicDamage * 15) / 10;
                break;
            default:
                damage = (basicDamage * 5) / 10;
                break;
            }
            p.takeDamage(damage);
        }
    }

    // Getters & Setters
    // Name
    /**
     * @return String of enemy name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the enemy name to the specified name.
     *
     * @param name String of the new enemy name
     */
    public void setName(String name) {
        this.name = name;
    }

    // ID
    /**
     * @return int of enemy id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the enemy id to the specified id.
     *
     * @param id int of the new enemy id
     */
    public void setId(int id) {
        this.id = id;
    }

    // Type
    /**
     * @return String of enemy type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the enemy type to the specified type.
     *
     * @param type String of the enemy type
     */
    public void setType(String type) {
        this.type = type;
    }

    // Maximum Health
    /**
     * @return int of enemy maximum health
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Sets the enemy maximum health to the specified maximum health.
     *
     * @param maxHealth int of the new enemy maximum health
     */
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    // Current Health
    /**
     * @return int of enemy current health
     */
    public int getCurrentHealth() {
        return currentHealth;
    }

    /**
     * Sets the enemy current health to the specified current health.
     *
     * @param currentHealth int of the new enemy current health
     */
    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    // Basic Damage
    /**
     * @return int of enemy basic damage dealt
     */
    public int getBasicDamage() {
        return basicDamage;
    }

    /**
     * Sets the enemy's basic damage dealt to the specified basic damage dealt.
     *
     * @param basicDamage int of new enemy basic damage dealt
     */
    public void setBasicDamage(int basicDamage) {
        this.basicDamage = basicDamage;
    }

    // Drops
    /**
     * @return Object[] of enemy drops
     */
    public Object[] getDrops() {
        return drops;
    }

    /**
     * Sets the enemy's list of drops.
     *
     * @param drops Object[] of new enemy drops
     */
    public void setDrops(Object[] drops) {
        this.drops = drops;
    }

    /**
     * Test for whether the enemy has been killed off. If so, should remove from all references.
     * @return boolean of whether the enemy has died or not
     */
    public boolean hasDied() {
        return this.isDead;
    }

    // Movement Strategy
    /**
     * @return MovementStrategy of enemy current movement strategy
     */
    public AutomatedMovementStrategy getMovementStrategy() {
        return movementStrategy;
    }

    /**
     * Sets the enemy movement strategy to the specified movement strategy.
     *
     * @param movementStrategy MovementStrategy of the new enemy movement strategy
     */
    public void setMovementStrategy(AutomatedMovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
    }

    /**
     * Returns the map that the enemy is bounded to.
     * @return GameMap of the enemy's map
     */
    public GameMap getMap() {
        return this.map;
    }

    // Count
    /**
     * @return int of total number of enemies of this type created
     */
    public static int getCount() {
        return count;
    }

    /**
     * Sets the counter for the total number of enemies created ot the specified amount.
     * ---
     * USE CASE: Delete all enemies, so set counter to 0.
     *
     * @param count int of the new count of enemies
     */
    public static void setCount(int count) {
        Enemy.count = count;
    }

    /**
     * Gets the enemy's X coordinate.
     * @return int of the enemy's X map coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * Gets the enemy's Y coordinate.
     * @return int of the enemy's Y map coordinate
     */
    public int getY() {
        return this.y;
    }

    /**
     * Sets the enemy's X coordinate.
     * @param x int of the enemy's X map coordinate
     */
    public void setX(int x) {
        if (!this.isActive()) {
            return;
        }
        this.x = x;
    }

    /**
     * Sets the enemy's Y coordinate.
     * @param y int of the enemy's Y map coordinate
     */
    public void setY(int y) {
        if (!this.isActive()) {
            return;
        }
        this.y = y;
    }

    /**
     * Gets the score that the enemy is worth when killed.
     * @return the score value of the enemy
     */
    public abstract int getScore();

    /**
     * Whether the enemy is active or not. Enemies may become deactivated if the map is changed.
     * @return boolean of whether the enemy is active or not
     */
    public boolean isActive() {
        return (!isDead && GameMap.getActiveMap() == this.map);
    }

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

} // FIN
