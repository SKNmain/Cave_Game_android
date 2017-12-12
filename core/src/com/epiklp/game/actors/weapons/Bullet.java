package com.epiklp.game.actors.weapons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.epiklp.game.actors.GameObject;

/**
 * Created by Asmei on 2017-12-04.
 */

public abstract class Bullet extends GameObject {


    protected boolean active;
    protected int hitPoint;

    public Bullet(Sprite sprite, int hitPoint){
        super(sprite);
        this.hitPoint = hitPoint;
    }

    public int getHitPoint(){return hitPoint;}
    public boolean isActive()
    {
        return active;
    }
}
