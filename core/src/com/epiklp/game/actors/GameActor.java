package com.epiklp.game.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Asmei on 2017-11-27.
 */

public abstract class GameActor extends Actor{

    protected float strengh;
    protected int life;
    protected float speedWalk;
    protected String name;

    protected Body body;
    protected Sprite sprite;


    public float getSpeedWalk() {
        return speedWalk;
    }

    public void setSpeedWalk(float speedWalk) {
        this.speedWalk = speedWalk;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public float getStrengh() {
        return strengh;
    }

    public void setStrengh(float strengh) {
        this.strengh = strengh;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life += life;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDead(){return (life <= 0);}
}

