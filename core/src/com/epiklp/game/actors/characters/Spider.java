package com.epiklp.game.actors.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.epiklp.game.functionals.Assets;
import com.epiklp.game.functionals.b2d.BodyCreator;

/**
 * Created by Asmei on 2017-11-29.
 */

public class Spider extends Enemy {

    public Spider(float x, float y) {
        super(new Sprite(Assets.MANAGER.get(Assets.textureAtlas).createSprite("spider", 0)), 38, 32);
        body = BodyCreator.createBody(x, y, false);
        BodyCreator.createBoxShape(body, 35f, 20f, 1f, 0);
        BodyCreator.createBoxSensor(body, 150f, 70f, new Vector2(0, 45f), GameCharacter.SENSORS.PATROL_SENSOR);

        setSensorAround(new Vector2(-45f, -28f),
                        new Vector2( 45f, -28f),
                        new Vector2(-49f, 20f),
                        new Vector2( 49f, 20f));


        body.setUserData(this);
        body.setGravityScale(30f);


        //light = TheBox.createPointLight(body, 64, new Color(1.000f, 0.498f, 0.314f, .75f), 10, 0, 0);
        initStats();
        setPatrolPoints();

        Array<Sprite> spritesForRunning = new Array<Sprite>();
        spritesForRunning.add(Assets.MANAGER.get(Assets.textureAtlas).createSprite("spider", 0));
        spritesForRunning.add(Assets.MANAGER.get(Assets.textureAtlas).createSprite("spider", 1));
        animator.addNewFrames(0.2f, spritesForRunning, STATE.RUNNING, Animation.PlayMode.LOOP);
        animator.addNewFrames(0.2f, spritesForRunning, STATE.IDLE, Animation.PlayMode.LOOP);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        animate(delta, state);
    }

    @Override
    protected void attack() {

    }

    @Override
    public void initStats() {
        this.actLife = this.maxLife = 30;
        this.attackSpeed = 2;
        this.runSpeed = 2.5f;
        this.strengh = 10;
        this.attackRange = 2.6f;
        this.patrolRange = 5f;
        state = STATE.RUNNING;
    }
}
