package com.epiklp.game.functionals.b2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

/**
 * Created by Asmei on 2018-02-12.
 */

public class JointCreator {
    private static RevoluteJointDef revDef = new RevoluteJointDef();

    public static void initialRevoluteJoint(Body a, Body b, boolean collideConnected, Vector2 localAnchorA, Vector2 localAnchorB) {
        revDef.collideConnected = collideConnected;
        revDef.bodyA = a;
        revDef.bodyB = b;
        revDef.localAnchorA.set(localAnchorA);
        revDef.localAnchorB.set(localAnchorB);
    }

    public static void initialRevoluteJoint(Body a, Body b, boolean collideConnected) {
        initialRevoluteJoint(a, b, collideConnected, new Vector2(0, 0), new Vector2(0, 0));
    }

    public static void setLimitForRevoluteJoint(boolean enable, float referenceAngle, float loweAngle, float upperAngle) {
        revDef.enableLimit = enable;
        revDef.referenceAngle = referenceAngle;
        revDef.lowerAngle = loweAngle;
        revDef.upperAngle = upperAngle;
    }

    public static void setLimitForRevoluteJoint(float loweAngle, float upperAngle) {
        setLimitForRevoluteJoint(true, 0f, loweAngle, upperAngle);
    }


    public static void setMotorForRevoluteJoint(boolean enable, float speed, float maxTorque) {
        revDef.enableMotor = enable;
        revDef.motorSpeed = speed;
        revDef.maxMotorTorque = maxTorque;
    }

    public static RevoluteJoint createRevoluteJoint() {
        return (RevoluteJoint) TheBox.world.createJoint(revDef);
    }
}
