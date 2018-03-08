package com.epiklp.game.actors.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.epiklp.game.Cave;
import com.epiklp.game.functionals.Assets;
import com.epiklp.game.functionals.b2d.BodyCreator;
import com.epiklp.game.functionals.b2d.JointCreator;
import com.epiklp.game.functionals.b2d.TheBox;
import com.epiklp.game.actors.characters.GameCharacter;

/**
 * Created by Asmei on 2018-02-11.
 */

public class Sword extends Weapon {
    private float time;
    private RevoluteJoint joint;

    public Sword(GameCharacter gameCharacterRef, int hitPoints, boolean turn){
        super(Assets.MANAGER.get(Assets.textureAtlas).createSprite("sejminar", -1), 22f, 38, hitPoints, gameCharacterRef);
        this.turn = turn;
        time = 0.4f;
        float x = gameCharacterRef.getBody().getPosition().x * Cave.PPM / Cave.SCALE;
        float y = gameCharacterRef.getBody().getPosition().y * Cave.PPM / Cave.SCALE;

        body = BodyCreator.createBody(x + (turn ? 18 : -18), y+16, false, false);
        BodyCreator.createBoxSensor(body, 10f, 37f, .00001f, 0, new Vector2(0,0), this);
        body.setUserData(this);
        body.setGravityScale(0);

        JointCreator.initialRevoluteJoint(gameCharacterRef.getBody(), body, false,
                new Vector2(0.5f * (turn ? 2f : -2f), 14/Cave.PPM), new Vector2(0, -34/Cave.PPM));

        if(turn){
            JointCreator.setLimitForRevoluteJoint(true, -2.2f, 0f, -1.5f);
            JointCreator.setMotorForRevoluteJoint(true, -8f, 50f);
        }else{
            JointCreator.setLimitForRevoluteJoint(true, -.25f, 0f, 2.0f);
            JointCreator.setMotorForRevoluteJoint(true, 8f, 70f);
        }

        joint = JointCreator.createRevoluteJoint();
    }

    float alpha = 0.1f;
    @Override
    public void act(float delta) {
        time -= delta;
        if ( time < .1){
            alpha += delta*2;
            sprite.setAlpha(alpha);
        }else if(time > .3){
            alpha -= delta;
            sprite.setAlpha(alpha);
        }else
            sprite.setAlpha(1);


        if (time < 0) {
            TheBox.addToDeleteArrayJoints(joint);
            setToDelete();
        }
    }

    @Override
    public void initStats() {
    }
}