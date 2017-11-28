package com.epiklp.game.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by epiklp on 19.11.17.
 */

public class Enemy extends GameActor {
    private float chanceOfDrop;

    public Enemy(Sprite sprite) {
        super(sprite);
    }

    public float getChanceOfDrop() {
        return chanceOfDrop;
    }

    public void setChanceOfDrop(float chanceOfDrop) {
        this.chanceOfDrop = chanceOfDrop;
    }
}
