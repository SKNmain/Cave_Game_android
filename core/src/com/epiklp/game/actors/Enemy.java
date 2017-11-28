package com.epiklp.game.actors;

/**
 * Created by epiklp on 19.11.17.
 */

public class Enemy extends GameActor {
    private float chanceOfDrop;

    public float getChanceOfDrop() {
        return chanceOfDrop;
    }

    public void setChanceOfDrop(float chanceOfDrop) {
        this.chanceOfDrop = chanceOfDrop;
    }
}
