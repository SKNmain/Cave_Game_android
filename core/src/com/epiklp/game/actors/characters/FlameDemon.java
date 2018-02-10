package com.epiklp.game.actors.characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.epiklp.game.Functional.Assets;
import com.epiklp.game.Functional.TheBox;

/**
 * Created by Asmei on 2017-11-29.
 */

public class FlameDemon extends Enemy {
    public FlameDemon(float x, float y) {
        super(new Sprite(Assets.manager.get(Assets.flameDemon)), 44, 57);
        body = TheBox.createBody(x, y, false);
        TheBox.createBoxShape(body, 30f, 50f, 25f, 0);
        TheBox.createBoxSensor(body, 200f, 90f, new Vector2(0, 45f), PATROL_SENSOR);
        body.setUserData(this);
        body.setGravityScale(50f);

        light = TheBox.createPointLight(body, 64, new Color(1.000f, 0.498f, 0.314f, .75f), 10, 0, 0);

        initStats();

    }

    @Override
    public void initStats() {
        this.actLife = 50;
        this.attackSpeed = 2;
        this.runSpeed = 4;
        this.strengh = 10;
        this.attackRange = 5f;
        this.patrolRange = 3f;
        setPatrolPoints();

    }
}
