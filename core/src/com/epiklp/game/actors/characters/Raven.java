package com.epiklp.game.actors.characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.epiklp.game.actors.weapons.EnemyMelee;
import com.epiklp.game.functionals.Assets;
import com.epiklp.game.functionals.b2d.BodyCreator;
import com.epiklp.game.functionals.b2d.TheBox;

/**
 * Created by Asmei on 2017-11-29.
 */

public class Raven extends Enemy {

    public Raven(float x, float y) {
        super(Assets.MANAGER.get(Assets.textureAtlas).createSprite("crown", 1), 40, 34);

        body = BodyCreator.createBody(x, y, false);
        BodyCreator.createBoxShape(body, 25f, 16f, 4f, 0);
        BodyCreator.createBoxSensor(body, 150f, 70f, new Vector2(0, 25f), SENSORS.PATROL_SENSOR);
        body.setUserData(this);
        //body.setGravityScale(10f);
        setSensorAround(new Vector2(10f, 40f), new Vector2(10f, 40f), new Vector2(5f, 5f), new Vector2(5f, 5f),
                        new Vector2(-10f, -25f), new Vector2(10f, -25f), new Vector2(-40f, 10f), new Vector2(40f, 10f),
                        new Vector2(10f, 50f), new Vector2(10f, 50f), new Vector2(-42f, 0f), new Vector2(42f, 0f));


        light = TheBox.createPointLight(body, 64, new Color(1.0f, 0.498f, 0.314f, .2f), true, 4, 0, 0);
        initStats();
        setPatrolPoints();

        Array<Sprite> spritesForRunning = Assets.MANAGER.get(Assets.textureAtlas).createSprites("crown");
        animator.addNewFrames(0.08f, spritesForRunning, STATE.RUNNING, Animation.PlayMode.LOOP);
        animator.addNewFrames(0.08f, spritesForRunning, STATE.IDLE, Animation.PlayMode.LOOP);
        animator.addNewFrames(0.08f, spritesForRunning, STATE.ATTACKING, Animation.PlayMode.LOOP);


    }


    @Override
    public void act(float delta) {
        super.act(delta);
        animate(delta, state);
    }

    @Override
    protected void attack() {
        EnemyMelee melee = new EnemyMelee(this, strengh, turn, attackSpeed);
        this.getStage().addActor(melee);
    }

    @Override
    public void initStats() {
        this.actLife = this.maxLife = 30;
        this.attackSpeed = 2;
        this.runSpeed = 5;
        this.strengh = 5;
        this.attackRange = 1.7f;
        this.patrolRange = 4f;
        this.flying = true;
        state = STATE.RUNNING;

    }
}
