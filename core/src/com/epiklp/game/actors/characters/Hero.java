package com.epiklp.game.actors.characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
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
    private boolean canClimb;
    private boolean wantToJump; //flaga od sterowania, gdy gracz chce skoczyć, nie ma nic wspólnego z wykrywaniem możliwości skoku!


    public int actMana;
    public int maxMana;
    public int maxAura;
    public int actAura;
    private float horizontalSpeed;

    private float climbingSpeed;

    private int onGround;
    private float damageTimeout;
    private float jumpTimeout;

    public Hero(float x, float y) {
        super(new Sprite(Assets.manager.get(Assets.player)), 64, 64);

        body = BodyCreator.createBody(x, y, false);
        BodyCreator.createBoxShape(body, 28, 60, 1f, 0f);


        BodyCreator.createBoxSensor(body, 14f, 10f, new Vector2(0, -60), SENSORS.JUMP_SENSOR);
        BodyCreator.createBoxSensor(body, 32f, 45f, new Vector2(0, -5), SENSORS.CLIMB_SENSOR);
        body.setUserData(this);
        //light = TheBox.createPointLight(body, 720, new Color(1.000f, 0.549f, 0.000f, .8f), 11, -2, -2);
        light = TheBox.createPointLight(body, 32, new Color(.9f, .6f, .3f, .9f), true, 11, -2, -2);


        Array<Sprite> sprites = new Array<Sprite>();
        sprites.add(Assets.manager.get(Assets.textureAtlas).createSprite("hero_idle",0));
        sprites.add(Assets.manager.get(Assets.textureAtlas).createSprite("hero_idle",1));
        animator.addNewFrames(0.5f, sprites, STATE.IDLE, Animation.PlayMode.LOOP);
        sprites.clear();
        sprites.add(Assets.manager.get(Assets.textureAtlas).createSprite("hero_run", 0));
        sprites.add(Assets.manager.get(Assets.textureAtlas).createSprite("hero_run",1));
        sprites.add(Assets.manager.get(Assets.textureAtlas).createSprite("hero_run",2));
        sprites.add(Assets.manager.get(Assets.textureAtlas).createSprite("hero_run",3));
        animator.addNewFrames(0.2f, sprites, STATE.RUNNING, Animation.PlayMode.LOOP);
        animator.addNewFrames(0.2f, sprites, STATE.CLIMBING, Animation.PlayMode.LOOP);



        initStats();

    }

    @Override
    public void initStats() {
        this.actLife = this.maxLife = this.maxAura = 100;
        this.actMana = this.maxMana = this.actAura = 100;
        this.attackSpeed = 0.8f;
        this.runSpeed = 3.5f;
        this.climbingSpeed = 3f;
        this.strengh = 10;
        state = STATE.IDLE;

    }


    @Override
    public void act(float delta) {
        animate(delta, state);

        attackDelta += delta;
        jumpTimeout--;


        if (state == STATE.RUNNING) {
            if (turn) {
                if (horizontalSpeed < getRunSpeed()) {
                    horizontalSpeed += 0.4f;
                }
            } else {
                if (horizontalSpeed > -getRunSpeed())
                    horizontalSpeed -= 0.4f;
                setSpeedX(horizontalSpeed);
            }
        } else if (state == STATE.IDLE) {
            if (horizontalSpeed > 0.4f) {
                horizontalSpeed -= 0.4f;
            } else if (horizontalSpeed < -0.4f) {
                horizontalSpeed += 0.4f;
            } else {
                horizontalSpeed = 0;
            }
        }
        setSpeedX(horizontalSpeed);

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

    public void setActMana(int mana) {
        this.actMana += mana;
        if (this.actMana > maxMana) this.actMana = maxMana;
    }
    public void setActAura(int timeAura) {
        this.actAura += timeAura;
        if (this.actAura > maxAura) this.actAura = maxAura;
    }

    public void getDamage(int damage) {
        if(damageTimeout < 0){
            setActLife(-damage);
            damageTimeout = 0.1f;
        }
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
        if (onGround > 0) {
            if (jumpTimeout <= 0) {
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
            setActMana(-10);
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
