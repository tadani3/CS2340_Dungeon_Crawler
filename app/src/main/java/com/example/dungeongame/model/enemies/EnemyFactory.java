package com.example.dungeongame.model.enemies;

import com.example.dungeongame.model.GameMap;

/**
 * Basic abstract class that details an EnemyFactor. An EnemyFactor has one abstract method,
 * createEnemy, and three defined methods: manufactureEnemy, getType, and setType.
 * ---
 * What is the Factory Design Pattern? The Factory Method Design Pattern is a creational pattern
 * that provides an interface for creating objects but allows subclasses to decide which class to
 * instantiate.
 * ---
 * Creates: Objects that extend abstract class Enemy
 * ---
 * Pattern: Factory Design Pattern
 * @author Ethan Nguyen-Tu
 * @version 1.0.0
 */
public abstract class EnemyFactory {
    private static String type;
    private static int count = 0;

    // FACTORY METHODS
    /**
     * Creates an enemy of the EnemyFactory's chosen type.
     *
     * @param map GameMap that the enemy will be created on
     * @param x int of the x starting coordinate of the enemy
     * @param y int of the y starting coordinate of the enemy
     * @return Enemy created
     */
    public abstract Enemy createEnemy(GameMap map, int x, int y);
    // Getters & Setters
    // Type
    /**
     * @return String of enemy type produced
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the enemy type produced to the specified type.
     *
     * @param type String of the enemy type produced
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return int of total number of enemies of this type manufactured by the factory
     */
    public static int getCount() {
        return count;
    }

}
