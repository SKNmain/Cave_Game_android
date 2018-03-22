package com.epiklp.game.actors.weapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.epiklp.game.Cave;
import com.epiklp.game.functionals.Assets;
import com.epiklp.game.functionals.OwnSound;
import com.epiklp.game.functionals.ParticlesManager;
import com.epiklp.game.functionals.b2d.BodyCreator;
import com.epiklp.game.functionals.b2d.TheBox;
import com.epiklp.game.actors.characters.GameCharacter;

import box2dLight.ConeLight;

/**
 * Created by epiklp on 29.11.17.
 */

public class FireBall extends Weapon {
    ParticleEffect bomb;
    public FireBall(GameCharacter gameCharacterRef, int hitPoints, boolean turn) {
        super(Assets.MANAGER.get(Assets.textureAtlas).createSprite("fireball"), 25, 30, hitPoints, gameCharacterRef);
        this.turn = turn;
        time = 3f;
        float x = gameCharacterRef.getBody().getPosition().x * Cave.PPM / Cave.SCALE;
        float y = gameCharacterRef.getBody().getPosition().y * Cave.PPM / Cave.SCALE;

        body = BodyCreator.createBody((x + (turn ? 14 : -14)), y, false);
        BodyCreator.createBoxSensor(body, 25f, 30f, 0.1f, 0f, null);
        //body.setTransform(x + (turn ? 2 : -2), y, 0);
        body.setGravityScale(0);
        body.setUserData(this);
        body.setBullet(true);
        body.applyLinearImpulse((turn ? 1 : -1) * 3.5f, 0, x, y, true);
        light = TheBox.createPointLight(body, 16, new Color(1.000f, 0.598f, 0.414f, 1f), true, 8, 0, 0);


        Array<Sprite> sprites = Assets.MANAGER.get(Assets.textureAtlas).createSprites("fireball");
        animator.addNewFrames(0.15f, sprites, STATE.ITEM, Animation.PlayMode.LOOP);

        OwnSound.playEffect(Assets.castingFlameSpell, 0.35f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        animate(delta, STATE.ITEM);
    }


    @Override
    public void setToDelete() {
        super.setToDelete();
    }

    @Override
    public void initStats() {
    }
}
