package com.epiklp.game.actors.traps;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.epiklp.game.functionals.Assets;
import com.epiklp.game.functionals.b2d.BodyCreator;

/**
 * Created by Asmei on 2018-03-08.
 */

public class Guillotine extends Trap{
    Body handlerBody;
    public Guillotine(Body handlerBody, float x, float y, int hitPoint){
        super(Assets.MANAGER.get(Assets.textureAtlas).createSprite("guillotine",-1), 50, 80, hitPoint);
        this.handlerBody = handlerBody;
        this.body = BodyCreator.createBody(x, y, true);
        BodyCreator.createBoxShape(body, 32f, 32f);
        BodyCreator.createBoxSensor(body, 16f, 20f);

    }

    @Override
    public void initStats() {

    }
}
