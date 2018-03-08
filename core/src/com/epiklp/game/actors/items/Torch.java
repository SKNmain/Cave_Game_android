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

    float timeToUpdateLight = 0;

    public Torch(float x, float y) {
        super(Assets.MANAGER.get(Assets.textureAtlas).createSprite("torch",0), 16, 20);

        body = BodyCreator.createBody(x, y, false);
        BodyCreator.createBoxSensor(body, 16f, 20f);
        light = TheBox.createPointLight(body, 32, new Color(1.000f, 0.8f, 0.814f, 0.7f), false, 11, 0, 0);
      //  light = TheBox.createPointLight(body, 32, new Color(0,0,1,1), false, 16, 0, 0);

        body.setGravityScale(0);


        Array<Sprite> sprites = Assets.MANAGER.get(Assets.textureAtlas).createSprites("torch");
        animator.addNewFrames(0.12f, sprites, STATE.ITEM, Animation.PlayMode.LOOP);
    }

    @Override
    public void act(float delta) {
        timeToUpdateLight += delta;
        if(timeToUpdateLight > 0.08){
            light.setDistance(11 + new Random().nextFloat()*2);
            timeToUpdateLight = 0;
        }
        super.act(delta);
        animate(delta, STATE.ITEM);
    }


    @Override
    public void initStats() {

    }
}
