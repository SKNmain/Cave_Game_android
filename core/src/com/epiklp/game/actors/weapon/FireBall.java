package com.epiklp.game.actors.weapon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;
import com.epiklp.game.Assets;
import com.epiklp.game.Cave;
import com.epiklp.game.TheBox;

/**
 * Created by epiklp on 29.11.17.
 */

public class FireBall extends Bullet{
    private float time;

    public FireBall(float x, float y, int hitPoint)
    {
        super(new Sprite(Assets.manager.get(Assets.FireBall)), hitPoint);
        body = TheBox.createBox(0, 0,32f,32f,false);
        sprite.setSize(0.3f * Cave.PPM * Cave.SCALE, 0.3f * Cave.PPM * Cave.SCALE);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2 + 1.f);
        body.setTransform((x + 2), y, 0f);
        body.setUserData(this);

        body.setBullet(true);
        active = true;
        time = 5f;

        body.applyLinearImpulse(70, 0, x, y, true);

    }
    @Override
    public void act(float delta)
    {
        //body.setLinearVelocity(200*delta, 0);
        time -= delta;
        if(time < 0)
        {
            setToDelete();
            active = false;
        }
    }


    @Override
    public void initStats() {

    }
}
