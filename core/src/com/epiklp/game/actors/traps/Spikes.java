package com.epiklp.game.actors.traps;

import com.badlogic.gdx.physics.box2d.Body;
import com.epiklp.game.functionals.Assets;

/**
 * Created by Asmei on 2018-03-08.
 */

public class Spikes extends Trap {
    public Spikes(Body body, int hitPoints) {
        super(Assets.MANAGER.get(Assets.textureAtlas).createSprite("fireball"), 1, 1, hitPoints);
        this.body = body;
        this.body.setGravityScale(0);
        this.body.setUserData(this);
        sprite.setAlpha(0);
    }

    @Override
    public void initStats() {

    }
}

