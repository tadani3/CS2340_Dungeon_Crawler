package com.example.dungeongame.model.powerups;

/**
 * Abstract rudimentary PowerUp class. A PowerUp class has one abstract method which is gainPower().
 * ---
 * The purpose of this class is to utilize polymorphism for decorating class instantiations. Any
 * class that extends PowerUp can use AttackPowerDecorator, HealthPowerDecorator, and
 * SafeTeleportPowerDecorator. Our player class extends from the PowerUp class so that a player may
 * gain Power ups through the use of the decorator pattern.
 * ---
 * NOTE: Recognize that the player is still singleton and we have only one instance of player.
 */
public abstract class PowerUp {
    public abstract void gainPower(String powerType);

}
