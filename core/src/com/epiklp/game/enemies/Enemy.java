package com.epiklp.game.enemies;

import com.badlogic.gdx.physics.box2d.Body;

public abstract class Enemy {
    private float strengh;
    private float life;
    private float speed;
    private String name;
    private float chanceOfDrop;

    private Body body;

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

    public float getLife() {
        return life;
    }

    public void setLife(float life) {
        this.life = life;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getChanceOfDrop() {
        return chanceOfDrop;
    }

    public void setChanceOfDrop(float chanceOfDrop) {
        this.chanceOfDrop = chanceOfDrop;
    }
}
