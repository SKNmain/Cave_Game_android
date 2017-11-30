package com.epiklp.game.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.epiklp.game.TheBox;

/**
 * Created by epiklp on 19.11.17.
 */

public class Enemy extends GameActor {
    private float chanceOfDrop;

    public Enemy(Sprite sprite) {
        super(sprite);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
    }

    public boolean isDead() {
        return life <= 0;
    }

    public float getChanceOfDrop() {
        return chanceOfDrop;
    }

    public void setChanceOfDrop(float chanceOfDrop) {
        this.chanceOfDrop = chanceOfDrop;
    }

    public void destroy() {
        TheBox.world.destroyBody(body);
        this.remove();
    }
}
