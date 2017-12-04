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

    public FireBall(float X, float Y)
    {
        super(new Sprite(Assets.manager.get(Assets.FireBall)));
        this.body = TheBox.createBox(0, 0,32f,32f,false);
        this.body.setTransform((X + 2), Y, 0f);
        active = true;
        time = 5;
    }
    @Override
    public void act(float delta)
    {
        body.setLinearVelocity(100*delta, 1);
        time -= delta;
        if(time < 0)
        {
            active = false;
        }
    }


    @Override
    public void initStats() {

    }
}
