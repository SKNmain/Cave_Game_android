package com.epiklp.game.actors.characters;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.epiklp.game.Functional.Assets;
import com.epiklp.game.Functional.TheBox;

/**
 * Created by Asmei on 2017-11-29.
 */

public class Spider extends Enemy {
    public Spider(float x, float y) {
        super(new Sprite(Assets.manager.get(Assets.textureAtlas).createSprite("spider", 0)), 38, 32);
        body = TheBox.createBody(x, y, false);
        TheBox.createBoxShape(body, 35f, 20f, 25f, 0);
        TheBox.createBoxSensor(body, 150f, 70f, new Vector2(0, 45f), PATROL_SENSOR);
        body.setUserData(this);
        body.setGravityScale(30f);


        //light = TheBox.createPointLight(body, 64, new Color(1.000f, 0.498f, 0.314f, .75f), 10, 0, 0);



        initStats();
        setPatrolPoints();

        Array<Sprite> spritesForRunning = new Array<Sprite>();
        spritesForRunning.add(Assets.manager.get(Assets.textureAtlas).createSprite("spider",0));
        spritesForRunning.add(Assets.manager.get(Assets.textureAtlas).createSprite("spider",1));
        animator.addNewFrames(0.2f, spritesForRunning, STATE.RUNNING, Animation.PlayMode.LOOP);

    }
    @Override
    public void act(float delta) {
        super.act(delta);
        animate(delta, state);
    }
    @Override
    public void initStats() {
        this.actLife = this.maxLife = 30;
        this.attackSpeed = 2;
        this.runSpeed = 2.5f;
        this.strengh = 10;
        this.attackRange = 5f;
        this.patrolRange = 2f;
        state = STATE.RUNNING;
    }
}
