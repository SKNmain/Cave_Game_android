package com.epiklp.game.actors.traps;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.epiklp.game.functionals.Assets;
import com.epiklp.game.functionals.b2d.BodyCreator;
import com.epiklp.game.functionals.b2d.JointCreator;

/**
 * Created by Asmei on 2018-03-08.
 */

public class Guillotine extends Trap {
    private Body handlerBody;
    private RevoluteJoint joint;

    public Guillotine(float x, float y, int hitPoint) {
        super(Assets.MANAGER.get(Assets.textureAtlas).createSprite("guillotine", -1), 50, 80, hitPoint);
        this.handlerBody = BodyCreator.createBody(x, y, true);
        BodyCreator.createBoxShape(this.handlerBody, 32f, 32f, 10f, 0);
        this.handlerBody.setGravityScale(10000f);


        this.body = BodyCreator.createBody(x, y - 5, false);
        this.body.setUserData(this);
        BodyCreator.createCircleShape(body, 32f,  1f, 0, new Vector2(0f, -2f), true);
        BodyCreator.createBoxSensor(body, 8f, 74f, 0f, 0f, new Vector2(0, 1f), null);
        body.setFixedRotation(false);


        JointCreator.initialRevoluteJoint(this.handlerBody,body, false, new Vector2(0,0), new Vector2(0, 2.5f));

        JointCreator.setLimitForRevoluteJoint(false, 0, 0, 0);
        JointCreator.setMotorForRevoluteJoint(true, MathUtils.random(3,4    ), 50000f);
        this.joint = JointCreator.createRevoluteJoint();
    }

    @Override
    public void initStats() {

    }
}
