package com.epiklp.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.epiklp.game.Assets;
import com.epiklp.game.Cave;
import com.epiklp.game.TheBox;

/**
 * Created by Asmei on 2017-11-29.
 */

public class FlameDemon extends Enemy {
    public FlameDemon() {
        super(new Sprite(Assets.manager.get(Assets.flameDemon)));

        // super(new Sprite(new Texture(Gdx.files.internal("enemies/FlameDemon.png"))));
        body = TheBox.createBox(600, 300, 30f, 50f, false);
    }
}
