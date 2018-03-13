package com.epiklp.game.actors.characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Array;
import com.epiklp.game.actors.characters.Enemy;
import com.epiklp.game.actors.traps.Trap;
import com.epiklp.game.actors.weapons.FireBall;
import com.epiklp.game.actors.weapons.Shootable;
import com.epiklp.game.actors.weapons.TowerFireBall;
import com.epiklp.game.functionals.Assets;
import com.epiklp.game.functionals.b2d.BodyCreator;
import com.epiklp.game.functionals.b2d.JointCreator;
import com.epiklp.game.functionals.b2d.TheBox;

/**
 * Created by Asmei on 2018-03-13.
 */

public class Tower extends Enemy implements Shootable{

    public Tower(float x, float y){
        super(Assets.MANAGER.get(Assets.textureAtlas).createSprite("tower", 0), 32, 64);
        this.body = BodyCreator.createKinematicBody(x, y+36);
        this.body.setUserData(this);
        BodyCreator.createBoxShape(body, 24, 60);
        BodyCreator.createBoxSensor(body, 256f, 60f, 0f, 0f, new Vector2(-250, 1f), SENSORS.LEFT_ATTACK_SENSOR);
        BodyCreator.createBoxSensor(body, 256f, 60f, 0f, 0f, new Vector2(250, 1f), SENSORS.RIGHT_ATTACK_SENSOR);

        light = TheBox.createStaticPointLight(64, new Color(0f, 0.598f, 0.814f, 0.1f), true, 4, body.getPosition().x, body.getPosition().y+2 );

        initStats();
        Array<Sprite> animationSprites = Assets.MANAGER.get(Assets.textureAtlas).createSprites("tower");
        animator.addNewFrames(0.2f, animationSprites, STATE.IDLE, Animation.PlayMode.LOOP);
        animator.addNewFrames(0.2f, animationSprites, STATE.ATTACKING, Animation.PlayMode.LOOP);
    }

    @Override
    public void initStats() {
        this.actLife = this.maxLife = 150;
        this.attackSpeed = 3.5f;
        this.attackTime = 1.1f;
        this.runSpeed = 0;
        this.strengh = 15;
        this.notMovable = true;
        state = STATE.IDLE;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        animate(delta, state);
        if(state == STATE.ATTACKING) light.setDistance(4f);
        else light.setDistance(2f);
    }

    @Override
    protected void attack() {
        shoot();
    }

    @Override
    public void shoot() {
        TowerFireBall fireBall = new TowerFireBall(this, strengh, getTurn());
        this.getStage().addActor(fireBall);
    }
}
