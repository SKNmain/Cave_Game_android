package com.epiklp.game.actors.characters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.epiklp.game.actors.GameObject;

/**
 * Created by Asmei on 2017-11-27.
 */

public abstract class GameCharacter extends GameObject {
    public enum SENSORS{
        JUMP_SENSOR, CLIMB_SENSOR, HEAD_SENSOR, PATROL_SENSOR, LEFT_DOWN_SENSOR, RIGHT_DOWN_SENSOR, LEFT_UP_SENSOR, RIGHT_UP_SENSOR
    }
    public int actLife;
    public int maxLife;

    protected int strengh;
    protected float runSpeed;
    protected float attackSpeed;
    protected float attackDelta;


    public abstract void initStats();

    public GameCharacter(Sprite sprite, float sizeX, float sizeY) {
        super(sprite, sizeX, sizeY);
    }

    @Override
    public void act(float delta) {
        attackDelta += delta;
    }

    public float getRunSpeed() {
        return runSpeed;
    }

    public void setRunSpeed(float runSpeed) {
        this.runSpeed = runSpeed;
    }

    public int getStrengh() {
        return strengh;
    }

    public void setStrengh(int strengh) {
        this.strengh = strengh;
    }

    public void setActLife(int actLife) {
        this.actLife += actLife;
        if(this.actLife > maxLife) this.actLife = maxLife;
        if (isDead())
            setToDelete();
    }

    public boolean isDead() {
        return (actLife <= 0);
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    protected void setState(GameCharacter.STATE state) {
        this.state = state;
    }

    protected void setSpeedY(float speedY) {
        body.setLinearVelocity(0, speedY);
    }

    protected void setSpeedX(float speedX) {
        body.setLinearVelocity(speedX, body.getLinearVelocity().y);
    }

}

