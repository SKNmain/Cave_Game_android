package com.epiklp.game.actors;

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

public class FireBall {
    private boolean alive, turn;
    private float time;
    private Body body;
    private Sprite sprite;

    public Body getBody() {
        return body;
    }



    public Sprite getSprite() {
        return sprite;
    }


    public FireBall(float X, float Y)
    {
        sprite = new Sprite(Assets.manager.get(Assets.FireBall));
        this.body = TheBox.createBox(0, 0,32f,32f,false);
        this.body.setTransform((X + 2), Y, 0f);
        alive = true;
        time = 5;
    }

    public void update(float delta)
    {
        body.setLinearVelocity(100*delta, 1);
        time -= delta;
        if(time < 0)
        {
            alive = false;
        }
    }

    public boolean getalive()
    {
        return alive;
    }
}
