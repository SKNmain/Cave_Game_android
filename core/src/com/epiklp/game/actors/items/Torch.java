package com.epiklp.game.actors.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.epiklp.game.actors.GameObject;
import com.epiklp.game.functionals.Assets;
import com.epiklp.game.functionals.b2d.BodyCreator;
import com.epiklp.game.functionals.b2d.TheBox;

import java.util.Random;

/**
 * Created by Asmei on 2018-02-12.
 */

public class Torch extends GameObject {

    public Torch(float x, float y) {
        super(Assets.manager.get(Assets.textureAtlas).createSprite("torch",0), 16, 20);

        body = BodyCreator.createBody(x, y, false);
        BodyCreator.createBoxSensor(body, 16f, 20f);
        light = TheBox.createPointLight(body, 64, new Color(1.000f, 0.498f, 0.314f, .9f), 12, 0, 0);
        body.setGravityScale(0);


        Array<Sprite> sprites = Assets.manager.get(Assets.textureAtlas).createSprites("torch");
        animator.addNewFrames(0.09f, sprites, STATE.ITEM, Animation.PlayMode.LOOP);
    }

    @Override
    public void act(float delta) {
       // light.setDistance(60 + new Random().nextFloat()*5);
        super.act(delta);
        animate(delta, STATE.ITEM);
    }


    @Override
    public void initStats() {

    }
}
