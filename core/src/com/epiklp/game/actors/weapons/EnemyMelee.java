package com.epiklp.game.actors.weapons;

import com.badlogic.gdx.graphics.g2d.Batch;
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

public class EnemyMelee extends Weapon {
    private float time;

    public EnemyMelee(GameCharacter gameCharacterRef, int hitPoints, boolean turn, float speed){
        super(Assets.manager.get(Assets.textureAtlas).createSprite("sejminar", -1), 22f, 38, hitPoints, gameCharacterRef);
        this.turn = turn;
        this.time = 1f;
        float x = gameCharacterRef.getBody().getPosition().x * Cave.PPM / Cave.SCALE;
        float y = gameCharacterRef.getBody().getPosition().y * Cave.PPM / Cave.SCALE;

        body = BodyCreator.createBody(x, y, false);
        BodyCreator.createBoxSensor(body, 25f, 30f, 1f, 0.1f, null);
        body.setGravityScale(0);
        body.setUserData(this);
        body.setBullet(true);
        body.applyLinearImpulse((turn ? 1 : -1) * speed, 0, x, y, true);
        sprite.setAlpha(0);
    }

    @Override
    public void act(float delta) {
        time -= delta;

        if (time < 0) {
            setToDelete();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

    }

    @Override
    public void initStats() {
    }
}