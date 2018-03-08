package com.epiklp.game.actors.traps;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.epiklp.game.Cave;
import com.epiklp.game.actors.GameObject;
import com.epiklp.game.functionals.AnimationCore;

/**
 * Created by Asmei on 2018-03-08.
 */

public abstract class Trap extends GameObject{
    protected int hitPoint;
    public Trap(Sprite sprite, float sizeX, float sizeY, int hitPoint) {
        super(sprite, sizeX, sizeY);
        this.hitPoint = hitPoint;
        sprite.setSize(sizeX * Cave.SCALE, sizeY * Cave.SCALE);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
    }

    public int getHitPoint(){
        return hitPoint;
    }
}
