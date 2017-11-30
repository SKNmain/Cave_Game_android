package com.epiklp.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.epiklp.game.Cave;

/**
 * Created by Asmei on 2017-11-27.
 */

public abstract class GameActor extends Actor{

    protected int strengh;
    protected int life;
    protected float speedWalk;
    protected String name;
    protected Body body;
    protected Sprite sprite;

    public GameActor(Sprite sprite) {
        this.sprite = sprite;
    }

    public float getSpeedWalk() {
        return speedWalk;
    }
    @Override
    public void draw(Batch batch, float parentAlpha){
        sprite.setPosition(body.getPosition().x*Cave.PPM-sprite.getWidth()/2, body.getPosition().y * Cave.PPM-sprite.getHeight()/2 );
        sprite.draw(batch);
    }
    public void setSpeedWalk(float speedWalk) {
        this.speedWalk = speedWalk;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public int getStrengh() {
        return strengh;
    }

    public void setStrengh(int strengh) {
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

