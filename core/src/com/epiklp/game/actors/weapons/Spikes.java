package com.epiklp.game.actors.weapons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.epiklp.game.Cave;
import com.epiklp.game.actors.GameObject;
import com.epiklp.game.actors.characters.GameCharacter;
import com.epiklp.game.functionals.Assets;
import com.epiklp.game.functionals.b2d.BodyCreator;
import com.epiklp.game.functionals.b2d.TheBox;

/**
 * Created by Asmei on 2018-03-08.
 */

public class Spikes extends  Weapon {
    public Spikes(Body body, int hitPoints) {
        super(Assets.MANAGER.get(Assets.textureAtlas).createSprite("fireball"), 1, 1, hitPoints, null);
        this.body = body;
        this.body.setGravityScale(0);
        this.body.setUserData(this);

        sprite.setAlpha(0);
    }

    @Override
    public void initStats() {

    }
}

