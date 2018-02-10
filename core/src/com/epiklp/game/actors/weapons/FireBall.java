package com.epiklp.game.actors.weapons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.epiklp.game.Cave;
import com.epiklp.game.Functional.Assets;
import com.epiklp.game.Functional.TheBox;
import com.epiklp.game.actors.characters.GameCharacter;

/**
 * Created by epiklp on 29.11.17.
 */

public class FireBall extends Bullet {
    private float time;

    public FireBall(float x, float y, int hitPoint, GameCharacter gameCharacterRef, boolean turn) {
        super(new Sprite(Assets.manager.get(Assets.textureAtlas).createSprite("fireball")), 25, 30, hitPoint, gameCharacterRef);
        this.turn = turn;
        time = 5f;

        body = TheBox.createBody(5, 0, false);
        TheBox.createBoxShape(body, 25f, 30f, 0.1f, 0);
        body.setTransform(x + (turn ? 2 : -2), y, 0);
        body.setGravityScale(0);
        body.setUserData(this);
        body.setBullet(true);
        body.applyLinearImpulse((turn ? 1 : -1) * 3.5f, 0, x, y, true);
        light = TheBox.createPointLight(body, 64, new Color(1.000f, 0.498f, 0.314f, .85f), 5, 0, 0);


        Array<Sprite> sprites = Assets.manager.get(Assets.textureAtlas).createSprites("fireball");
        animator.addNewFrames(0.15f, sprites, STATE.ITEM, Animation.PlayMode.LOOP);
    }

    @Override
    public void act(float delta) {
        time -= delta;
        animate(delta, STATE.ITEM);
        if (time < 0) {
            setToDelete();
        }
    }

    @Override
    public void initStats() {
    }
}
