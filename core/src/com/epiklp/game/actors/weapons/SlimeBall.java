package com.epiklp.game.actors.weapons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.epiklp.game.Cave;
import com.epiklp.game.actors.characters.GameCharacter;
import com.epiklp.game.functionals.Assets;
import com.epiklp.game.functionals.OwnSound;
import com.epiklp.game.functionals.b2d.BodyCreator;
import com.epiklp.game.functionals.b2d.TheBox;

/**
 * Created by epiklp on 29.11.17.
 */

public class SlimeBall extends Weapon{

    public SlimeBall(GameCharacter gameCharacterRef, int hitPoints, boolean turn, float impulseY) {
        super(Assets.MANAGER.get(Assets.textureAtlas).createSprite("slime_ball"), 20, 15, hitPoints, gameCharacterRef);
        this.turn = turn;
        time = 3f;
        float x = gameCharacterRef.getBody().getPosition().x * Cave.PPM / Cave.SCALE;
        float y = gameCharacterRef.getBody().getPosition().y * Cave.PPM / Cave.SCALE;

        body = BodyCreator.createBody((x + (turn ? 14 : -14)), y, false);
        BodyCreator.createBoxSensor(body, 20f, 15f, 0.1f, 0, null);

        body.setGravityScale(0);
        body.setUserData(this);
        body.setBullet(true);
        body.applyLinearImpulse((turn ? 1 : -1) * 1.0f, impulseY, x, y, true);
        light = TheBox.createPointLight(body, 64, new Color(0f, 0.598f, 0.814f, 1f), true, 8, 0, 0);


        Array<Sprite> sprites = Assets.MANAGER.get(Assets.textureAtlas).createSprites("slime_ball");
        animator.addNewFrames(0.15f, sprites, STATE.ITEM, Animation.PlayMode.LOOP);

        OwnSound.playEffect(Assets.castingFlameSpell, 0.35f);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        animate(delta, STATE.ITEM);
    }

    @Override
    public void initStats() {
    }


}
