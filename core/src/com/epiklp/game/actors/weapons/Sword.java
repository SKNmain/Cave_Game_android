package com.epiklp.game.actors.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.epiklp.game.Cave;
import com.epiklp.game.actors.characters.GameCharacter;
import com.epiklp.game.functionals.Assets;
import com.epiklp.game.functionals.b2d.BodyCreator;
import com.epiklp.game.functionals.b2d.JointCreator;
import com.epiklp.game.functionals.b2d.TheBox;

/**
 * Created by Asmei on 2018-02-11.
 */

public class Sword extends Weapon {
    private float time;
    RevoluteJoint joint;

    public Sword(GameCharacter gameCharacterRef, int hitPoints, boolean turn) {
        super(Assets.manager.get(Assets.textureAtlas).createSprite("sword", -1), 22f, 34, hitPoints, gameCharacterRef);
        this.turn = turn;
        time = 4f;
        float x = gameCharacterRef.getBody().getPosition().x * Cave.PPM / Cave.SCALE;
        float y = gameCharacterRef.getBody().getPosition().y * Cave.PPM / Cave.SCALE;

        body = BodyCreator.createBody(x + (turn ? 22 : -22), y + 30, false, false);
        BodyCreator.createBoxSensor(body, 20f, 34f, .00001f, 0, new Vector2(0, 0), this);
        //BodyCreator.createShape(body, 0.0001f, 0f, new Vector2(0, -1.3f), new Vector2(1.3f, 1.5f), new Vector2(-1.3f, 1.5f));
        body.setUserData(this);
        body.setGravityScale(0);
//        body.setTransform(x + (turn ? 22 : -22), y+30, (turn ? -.25f : .25f));

        JointCreator.initialRevoluteJoint(gameCharacterRef.getBody(), body, false,
                new Vector2(0.6f * (turn ? 2 : -2), 30 / Cave.PPM), new Vector2(0, -34 / Cave.PPM));
        //JointCreator.initialRevoluteJoint(gameCharacterRef.getBody(), body, false,
        //                new Vector2(0.5f * (turn ? 2 : -2), 30/Cave.PPM), new Vector2(-0.75f, -1.1f));

        if (turn) {
            JointCreator.setLimitForRevoluteJoint(true, -1.7f, 0f, -1.5f);
            JointCreator.setMotorForRevoluteJoint(true, -12f, 50f);
        } else {
            JointCreator.setLimitForRevoluteJoint(0f, 1.7f);
            JointCreator.setMotorForRevoluteJoint(true, 12f, 50f);
        }


        joint = JointCreator.createRevoluteJoint();
    }


    @Override
    public void act(float delta) {
        time -= delta;
        if (time < 0) {
            TheBox.addToDeleteArrayJoints(joint);
            setToDelete();
        }
    }

    @Override
    public void initStats() {
    }
}