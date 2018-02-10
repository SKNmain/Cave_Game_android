package com.epiklp.game.actors.characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.epiklp.game.Functional.Assets;
import com.epiklp.game.Functional.TheBox;
import com.epiklp.game.actors.weapons.FireBall;
import com.epiklp.game.actors.weapons.Shootable;

/**
 * Created by epiklp on 14.11.17.
 */


public class Hero extends GameCharacter implements Shootable {


    public static final String JUMP_SENSOR = "JUMP_SEN";
    public static final String CLIMB_SENSOR = "CLIMB_SEN";

    private boolean canClimb;
    private boolean wantToJump; //flaga od sterowania, gdy gracz chce skoczyć, nie ma nic wspólnego z wykrywaniem możliwości skoku!


    public int actMana;
    public int maxMana;

    private float climbingSpeed;

    private int onGround = 0;
    private float jumpTimeout = 0;

    public Hero(float x, float y) {
        super(new Sprite(Assets.manager.get(Assets.player)), 64, 64);

        body = TheBox.createBody(x, y, false);
        TheBox.createBoxShape(body, 28, 60, 1f, 0); //zmieniona postac ma 64x64 wiec zostałoa zmieniona wilkosc boxa i obraka
        TheBox.createBoxSensor(body, 10f, 10f, new Vector2(0, -60), JUMP_SENSOR);
        TheBox.createBoxSensor(body, 35f, 45f, new Vector2(0, -5), CLIMB_SENSOR);
        body.setUserData(this);
        light = TheBox.createPointLight(body, 720, new Color(1.000f, 0.549f, 0.000f, .8f), 10, -2, -2);
        initStats();

    }

    @Override
    public void initStats() {
        this.actLife = this.maxLife = 100;
        this.actMana = this.maxMana = 100;
        this.attackSpeed = 0.7f;
        this.runSpeed = 3.5f;
        this.climbingSpeed = 3f;
        this.strengh = 10;
        state = STATE.IDLE;
    }

    float hor = 0;

    @Override
    public void act(float delta) {

        attackDelta += delta;
        jumpTimeout--;

        if (state == STATE.RUNNING) {
            if (turn) {
                if (hor < getRunSpeed()) {
                    hor += 0.4f;
                }
            } else {
                if (hor > -getRunSpeed())
                    hor -= 0.4f;
                setSpeedX(hor);
            }
        } else if (state == STATE.IDLE) {
            if (hor > 0.1f) {
                hor -= 0.2f;
            } else if (hor < -0.1f) {
                hor += 0.2f;
            } else {
                hor = 0;
            }
        }
        setSpeedX(hor);

        if (state == STATE.CLIMBING && canClimbing()) {
            climb();
        } else if (wantToJump) {
            jump();
        }

        if (state == STATE.IDLE && canClimbing()) {//opuszczanie się po ściance
            setSpeedY(-0.5f);
        }

    }

    public void wantToMoveRight() {
        state = STATE.RUNNING;
        turn = true;
    }

    public void wantToMoveLeft() {
        state = STATE.RUNNING;
        turn = false;
    }

    @Override
    public float getRunSpeed() {
        return runSpeed + actLife * 0.03f;
    }
    public void setMagic(int mana) {
        this.actMana += mana;
        if(this.actMana > maxMana) this.actMana = maxMana;
    }

    public void outGround() {
        this.onGround--;
    }

    public void onGround() {
        this.onGround++;
    }

    public boolean canClimbing() {
        return canClimb;
    }

    public void setCanClimb(boolean canClimb) {
        this.canClimb = canClimb;
    }

    public void wantToJump() {
        wantToJump = true;
    }

    public void wantToClimb() {
        state = STATE.CLIMBING;
    }

    public void wantToIdle() {
        state = STATE.IDLE;
    }

    private void jump() {
        if (jumpTimeout <= 0) {
            if (onGround > 0) {
                body.setLinearVelocity(0, body.getMass() * 2.05f);
                jumpTimeout = 65f;
            }
        }
        wantToJump = false;
    }

    private void climb() {
        setSpeedY(body.getMass() * 0.8f);
    }

    @Override
    public void shoot() {
        if (actMana > 10 && attackSpeed <= attackDelta) {
            setMagic(-10);
            FireBall fireBall = new FireBall(body.getPosition().x, body.getPosition().y, strengh, this, getTurn());
            this.getStage().addActor(fireBall);
            activeBullets.add(fireBall);
            attackDelta = 0;
        }
    }
}
