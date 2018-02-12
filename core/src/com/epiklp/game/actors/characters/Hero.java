package com.epiklp.game.actors.characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.epiklp.game.functionals.Assets;
import com.epiklp.game.functionals.b2d.BodyCreator;
import com.epiklp.game.functionals.b2d.TheBox;
import com.epiklp.game.actors.weapons.FireBall;
import com.epiklp.game.actors.weapons.Shootable;
import com.epiklp.game.actors.weapons.Sword;

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

        body = BodyCreator.createBody(x, y, false);
        BodyCreator.createBoxShape(body, 28, 60, 1f, 0);
        BodyCreator.createBoxSensor(body, 10f, 10f, new Vector2(0, -60), JUMP_SENSOR);
        BodyCreator.createBoxSensor(body, 32f, 45f, new Vector2(0, -5), CLIMB_SENSOR);
        body.setUserData(this);
       // light = TheBox.createPointLight(body, 720, new Color(1.000f, 0.549f, 0.000f, .8f), 10, -2, -2);
        initStats();

    }

    @Override
    public void initStats() {
        this.actLife = this.maxLife = 100;
        this.actMana = this.maxMana = 100;
        this.attackSpeed = 0.8f;
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
        if (this.actMana > maxMana) this.actMana = maxMana;
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
                body.setLinearVelocity(0, 16.5f);
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
            FireBall fireBall = new FireBall(this, strengh, getTurn());
            this.getStage().addActor(fireBall);
            activeBullets.add(fireBall);
            attackDelta = 0;
        }
    }

    //zrobię to też interfejsem Melee
    public void meleeAttack() {
        if (attackSpeed <= attackDelta) {
            attackDelta = 0;
            Sword sword = new Sword(this, strengh, turn);
            this.getStage().addActor(sword);
        }
    }
}
