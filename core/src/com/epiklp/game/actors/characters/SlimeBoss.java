package com.epiklp.game.actors.characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.epiklp.game.actors.weapons.EnemyMelee;
import com.epiklp.game.actors.weapons.Shootable;
import com.epiklp.game.actors.weapons.SlimeBall;
import com.epiklp.game.functionals.Assets;
import com.epiklp.game.functionals.b2d.BodyCreator;
import com.epiklp.game.functionals.b2d.TheBox;

/**
 * Created by Asmei on 2018-03-07.
 */

public class SlimeBoss extends Enemy implements Shootable {

    private Hero hero;

    public SlimeBoss(float x, float y) {
        super(Assets.MANAGER.get(Assets.textureAtlas).createSprite("slime_run", 0), 50, 40);

        body = BodyCreator.createBody(x, y, false);
        BodyCreator.createBoxShape(body, 30, 25, 10f, 0);
        //Sensors
        BodyCreator.createBoxSensor(body, 30f, 20f, new Vector2(0, 0f), SENSORS.PATROL_SENSOR);
        setSensorAround(new Vector2(-30f, -25f), new Vector2(30f, -25f), new Vector2(-30f, 25f), new Vector2(30f, 25f),
                new Vector2(20f, 40f), new Vector2(20f, 40f), new Vector2(-30f, 0f), new Vector2(30f, 0f));
        body.setUserData(this);
        body.setGravityScale(30f);
        initStats();
        setPatrolPoints();

        light = TheBox.createPointLight(body, 64, new Color(0.007f, 0.468f, 0.914f, .1f), true, 13, 0, 0);


        Array<Sprite> animationSprites = Assets.MANAGER.get(Assets.textureAtlas).createSprites("slime_idle");
        animator.addNewFrames(0.2f, animationSprites, STATE.IDLE, Animation.PlayMode.LOOP);
        animationSprites = Assets.MANAGER.get(Assets.textureAtlas).createSprites("slime_run");
        animator.addNewFrames(0.2f, animationSprites, STATE.RUNNING, Animation.PlayMode.LOOP);
        animationSprites = Assets.MANAGER.get(Assets.textureAtlas).createSprites("slime_attack");
        animator.addNewFrames(0.20f, animationSprites, STATE.ATTACKING, Animation.PlayMode.LOOP);
    }

    public void positionHero(Hero hero) {
        this.hero = hero;
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        animate(delta, state);
        if (heroLastPos != null) {
            following = attacked = true;
        }
    }

    protected void attack() {
        EnemyMelee melee = new EnemyMelee(this, strengh, turn, 20, 25f, 60f, 1f);
        this.getStage().addActor(melee);
    }

    @Override
    public void initStats() {
        this.actLife = this.maxLife = 100;
        this.attackSpeed = 5.5f;
        this.attackTime = 1.1f;
        this.runSpeed = 2.4f;
        this.strengh = 20;
        this.attackRange = 4.1f;
        this.patrolRange = 5f;
        state = STATE.IDLE;
        turn = false;
        notMovable = true;
    }

    @Override
    public void shoot() {
        if (attackSpeed <= attackDelta) {
            SlimeBall slimeBall = new SlimeBall(this, 10, getTurn(), -0.25f);
            this.getStage().addActor(slimeBall);
            SlimeBall slimeBall1 = new SlimeBall(this, 10, getTurn(), 0.4f);
            this.getStage().addActor(slimeBall1);
            SlimeBall slimeBall2 = new SlimeBall(this, 10, getTurn(), 0.85f);
            this.getStage().addActor(slimeBall2);
            SlimeBall slimeBall3 = new SlimeBall(this, 10, getTurn(), 0.12f);
            this.getStage().addActor(slimeBall3);
            attackDelta = 0;
        }
    }


    protected void moving() {
        if (notMovable) {
            wantToAttack();
        }
        if(hero.getBody().getPosition().x < body.getPosition().x) {
            state = STATE.RUNNING;
            turn = false;
            body.setLinearVelocity(-runSpeed, body.getLinearVelocity().y);
        }
        else
        {
            state = STATE.RUNNING;
            turn = true;
            body.setLinearVelocity(runSpeed, body.getLinearVelocity().y);
        }
    }

    protected void wantToAttack() {
        if (attackDelta >= attackSpeed) {
            if (leftAttackSensor) {
                attack();
                state = STATE.ATTACKING;
                attackDelta = elapsedTime = 0;
            } else shoot();

            attackTimeout = 0;
        } else if (attackTimeout >= attackTime) {
            state = STATE.IDLE;
        }

    }


}
