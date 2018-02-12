package com.epiklp.game.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.epiklp.game.Cave;
import com.epiklp.game.functionals.AnimationCore;
import com.epiklp.game.functionals.b2d.TheBox;

import box2dLight.Light;

/**
 * Created by Asmei on 2017-11-27.
 */

public abstract class GameObject extends Actor {
    public enum STATE {
        CLIMBING, IDLE, ATTACKING, RUNNING, ITEM
    }

    protected STATE state;

    //true - "watching" to right side of screen
    protected boolean turn = true;
    protected float elapsedTime = 0;

    protected Body body;
    protected Sprite sprite;
    protected AnimationCore animator;
    protected Light light;

    public abstract void initStats();

    public GameObject(Sprite sprite, float sizeX, float sizeY) {
        this.sprite = sprite;
        sprite.setSize(sizeX * Cave.SCALE, sizeY * Cave.SCALE);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        animator = new AnimationCore(sizeX, sizeY);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.setFlip(!turn, false);
        sprite.setRotation((float)Math.toDegrees(body.getAngle()));
        sprite.setPosition(body.getPosition().x * Cave.PPM - sprite.getWidth() / 2, body.getPosition().y * Cave.PPM - sprite.getHeight() / 2);
        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void destroy() {
        if (light != null) {
            light.remove();
            light = null;
        }
        if (body != null) {
            TheBox.destroyBody(body);
            body = null;
            this.remove();
        }
    }

    public void setToDelete() {
        TheBox.addToDeleteArray(this);
    }

    public boolean getTurn() {
        return turn;
    }

    public Body getBody() {
        return body;
    }

    //if you want to start animating something, you must add frames to animator and call this function in act
    protected void animate(float delta, STATE state){
        elapsedTime += delta;
        sprite = animator.getFrame(elapsedTime, turn, state);
    }

}

