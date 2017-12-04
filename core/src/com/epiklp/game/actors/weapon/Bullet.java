package com.epiklp.game.actors.weapon;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.epiklp.game.actors.GameObject;

/**
 * Created by Asmei on 2017-12-04.
 */

public abstract class Bullet extends GameObject {


    public boolean active, turn;
    public Bullet(Sprite sprite){
        super(sprite);
    }

    public boolean isActive()
    {
        return active;
    }
}
