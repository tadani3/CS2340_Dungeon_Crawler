package com.example.dungeongame.model.powerups;

/**
 * This is another simple class that specifies methods specific to all PowerDecorators as all
 * decorators extend this class. Yes, this class doesn't really do anything at the moment. The
 * purpose is to mask itself as a PowerUp so that the decorator pattern functions properly. If it
 * is needed, this is where instance data or methods specific to PowerDecorator's would go.
 */
public abstract class PowerDecorator extends PowerUp {

    public abstract void gainPower(String powerType);
}
