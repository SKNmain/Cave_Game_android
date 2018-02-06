package com.epiklp.game.actors.characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.epiklp.game.Cave;
import com.epiklp.game.Functional.Assets;
import com.epiklp.game.Functional.TheBox;
import com.epiklp.game.actors.weapons.FireBall;
import com.epiklp.game.actors.weapons.Shootable;

/**
 * Created by epiklp on 14.11.17.
 */


public class Hero extends GameCharacter implements Shootable {


    public static final String JUMP_SENSOR = "JUMP_SEN";
    public static final String CLIMB_SEN = "CLIMB_SEN";

    public enum STATE {
        CLIMBING, RUNNING, STANDING, JUMPING;
    }

    private int magic;

    private float climbingSpeed;
    private STATE state = STATE.STANDING;

    private int onGround = 0;
    private float jumpTimeout = 0;
    private float climbingTimeout = 0;

    public Hero(float x, float y) {
        super(new Sprite(Assets.manager.get(Assets.player)));
        sprite.setSize(64*Cave.SCALE, 64*Cave.SCALE);
        sprite.setOrigin(64*Cave.SCALE, 64*Cave.SCALE);
        setBody(TheBox.createBody(x, y, false));
        TheBox.createBoxShape(body, 28, 60, 0.8f, 1f); //zmieniona postac ma 64x64 wiec zostałoa zmieniona wilkosc boxa i obraka
        TheBox.createBoxSensor(body, 10f, 10f, new Vector2(0, -60), JUMP_SENSOR);
        TheBox.createBoxSensor(body, 35f, 30f, new Vector2(0, 0), CLIMB_SEN);
        body.setUserData(this);
        light = TheBox.createPointLight(body, 720, new Color(1.000f, 0.549f, 0.000f, .7f), 10, -2, -2);
        initStats();
    }

    @Override
    public void initStats() {
        this.life = 100;
        this.magic = 100;
        this.attackSpeed = 0.7f;
        this.speedWalk = 3.5f;
        this.climbingSpeed = 3f;
        this.strengh = 10;
    }

    float hor = 0;

    @Override
    public void act(float delta) {
       // System.out.println(getBody().getPosition().x + "        " + getBody().getPosition().y);

        attackDelta += delta;
        jumpTimeout--;
        climbingTimeout--;

        if (turn) sprite.setFlip(false, false);
        else sprite.setFlip(true, false);

        if (state == STATE.RUNNING) {
            if (turn) {
                if (hor < getSpeedWalk())
                    hor += 0.4f;
            } else if (!turn) {
                if (hor > -getSpeedWalk())
                    hor -= 0.4f;
            }
            setSpeedX(hor);
        } else if (state == STATE.STANDING) {
            if (hor > 0.1f) {
                hor -= 0.2f;
            } else if (hor < -0.1f) {
                hor += 0.2f;
            } else {
                hor = 0;
            }
            setSpeedX(hor);
        } else if (state == STATE.CLIMBING) {
            climb();
        }
    }

    @Override
    public float getSpeedWalk() { 
        return speedWalk + life * 0.03f;
    }

    public int getMagic() {
        return magic;
    }

    public int getOnGround() {
        return onGround;
    }

    public void setMagic(int magic) {
        this.magic += magic;
    }

    public void outGround() {
        this.onGround--;
    }

    public void onGround() {
        this.onGround++;
    }

    public void setState(Hero.STATE state) {
        this.state = state;
    }

    public void setSpeedX(float speedX) {
        body.setLinearVelocity(speedX, body.getLinearVelocity().y);
    }

    public void climb() {
        if (climbingTimeout <= 0) {
            body.setLinearVelocity(0, body.getMass() * 2f);
        }
    }

    public STATE getState() {
        return state;
    }

    public void jump() {
        if (jumpTimeout <= 0) {
            if (onGround > 0) {
                body.setLinearVelocity(0, body.getMass() * 3.3f);
                jumpTimeout = 70f;
            }
        }
    }

    public void setSpeedY(float speedY) {
        body.setLinearVelocity(0, speedY);
    }

    @Override
    public void shoot() {
        if (getMagic() > 10 && attackSpeed <= attackDelta) {
            setMagic(-10);
            FireBall fireBall = new FireBall(body.getPosition().x, body.getPosition().y, strengh, this, getTurn());
            this.getStage().addActor(fireBall);
            activeBullets.add(fireBall);
            attackDelta = 0;
        }
    }
}
