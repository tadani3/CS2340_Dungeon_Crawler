package com.example.dungeongame.model.powerups;

import com.example.dungeongame.model.GameMap;
import com.example.dungeongame.model.Player;
import com.example.dungeongame.model.enemies.Locatable;
import com.example.dungeongame.viewmodels.observation.CollisionObserver;
import com.example.dungeongame.viewmodels.observation.Observer;
import com.example.dungeongame.viewmodels.observation.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the class for the actual item that will be displayed. It needs to have collisions so it
 * implements Subject. It is essentially just a copy paste from Enemy because the powerUp items
 * are really just entities / enemies when you think about it.
 */
public class PowerUpItem implements Subject, Locatable {
    private String name;
    private GameMap map;
    private final int x;
    private final int y;
    private boolean exists = false;
    private List<Observer> observers = new ArrayList<>();

    public PowerUpItem(String name, GameMap map, int x, int y) {
        this.name = name;
        this.map = map;
        this.x = x;
        this.y = y;
        this.exists = true;
        this.addObserver(CollisionObserver.getColObserver());
        if (map != null) {
            map.addPowerUpItem(this);
        }

    }

    /**
     * acts as a kill method
     */
    public void removeItem() {
        map.removePowerUpItem(this);
    }


    /**
     * The checkCollisions method is where the magic of the decorator pattern occurs. If the player
     * collides, (AKA consumes) the PowerUpItem, it will receive its respective decoration dependent
     * on the PowerUpItem's type specified by the String name variable. Notice how the Player is
     * never re-instantiated due to singleton. Yet, it is still decorator with PowerDecorator's and
     * resigned. This is due to the player class extending from PowerUp and polymorphism.
     */
    public void checkCollision() {
        PowerUp player = Player.getPlayer();
        String[] activePowers = ((Player) player).getActivePowerNames();
        if (!this.isActive()) {
            return;
        }
        if (((Player) player).getX() == this.x && ((Player) player).getY() == this.y) {
            if (this.name.equals("health")) {
                ((Player) player).setActivePowerName(0, name);
                player = new HealthPowerDecorator(player);
            } else if (this.name.equals("teleport")) {
                ((Player) player).setActivePowerName(1, name);
                player = new SafeTeleportPowerDecorator(player);
            } else if (this.name.equals("attack")) {
                ((Player) player).setActivePowerName(2, name);
                player = new AttackPowerDecorator(player);
            } else if (this.name.equals("money")) {
                ((Player) player).setActivePowerName(3, name);
                player = new ScorePowerDecorator(player);
            }
            player.gainPower(name);
            removeItem();


        }
    }


    /**
     * Whether the item is active or not. Items may become deactivated if the map is changed.
     * @return boolean of whether the Item is active or not
     */
    public boolean isActive() {
        return (exists && GameMap.getActiveMap() == this.map);
    }

    //Observation Methods
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
    public String getPowerName() {
        return this.name;
    }
    /**
     * Gets the items's X coordinate.
     * @return int of the items's X map coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * Gets the items's Y coordinate.
     * @return int of the items's Y map coordinate
     */
    public int getY() {
        return this.y;
    }
}
