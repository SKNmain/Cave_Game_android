package com.epiklp.game.actors.characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.epiklp.game.actors.weapons.EnemyMelee;
import com.epiklp.game.actors.weapons.FireBall;
import com.epiklp.game.functionals.Assets;
import com.epiklp.game.functionals.b2d.BodyCreator;
import com.epiklp.game.functionals.b2d.TheBox;

/**
 * Created by Asmei on 2018-03-07.
 */

public class Slime extends Enemy {

    public Slime(float x, float y) {
        super(Assets.manager.get(Assets.textureAtlas).createSprite("slime_run", 0), 50, 40);

        body = BodyCreator.createBody(x, y, false);
        BodyCreator.createBoxShape(body, 34f, 32f, 10f,0);
        //Sensors
        BodyCreator.createBoxSensor(body, 150f, 65f, new Vector2(0, 45f), SENSORS.PATROL_SENSOR);
        setSensorAround(new Vector2(-40f, -40f), new Vector2( 40f, -40f), new Vector2(-45f,  16f), new Vector2( 45f,  16f),
                        new Vector2(15f, 50f), new Vector2(15f, 50f), new Vector2(-42f, 0f), new Vector2(42f, 0f));
        body.setUserData(this);
        body.setGravityScale(30f);
        initStats();
        setPatrolPoints();

        //light = TheBox.createPointLight(body, 64, new Color(1.000f, 0.498f, 0.314f, .75f), 10, 0, 0);


        Array<Sprite> animationSprites = Assets.manager.get(Assets.textureAtlas).createSprites("slime_idle");
        animator.addNewFrames(0.2f, animationSprites, STATE.IDLE, Animation.PlayMode.LOOP);
        animationSprites = Assets.manager.get(Assets.textureAtlas).createSprites("slime_run");
        animator.addNewFrames(0.2f, animationSprites, STATE.RUNNING, Animation.PlayMode.LOOP);
        animationSprites = Assets.manager.get(Assets.textureAtlas).createSprites("slime_attack");
        animator.addNewFrames(0.20f, animationSprites, STATE.ATTACKING, Animation.PlayMode.LOOP);

    }


    @Override
    public void act(float delta) {
        super.act(delta);
        animate(delta, state);
    }

    protected void attack() {
        EnemyMelee melee = new EnemyMelee(this, strengh, turn, attackSpeed);
        this.getStage().addActor(melee);
    }

    @Override
    public void initStats() {
        this.actLife = this.maxLife = 50;
        this.attackSpeed = 3.5f;
        this.attackTime = 1.1f;
        this.runSpeed = 1.4f;
        this.strengh = 15;
        this.attackRange = 2.4f;
        this.patrolRange = 5f;
        state = STATE.RUNNING;
    }
}
