package com.epiklp.game.actors.weapons;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.epiklp.game.actors.GameObject;
import com.epiklp.game.actors.characters.GameCharacter;

/**
 * Created by Asmei on 2017-12-04.
 */

public abstract class Weapon extends GameObject {

    protected float time;
    protected GameCharacter gameCharacter;
    protected int hitPoint;

    public Weapon(Sprite sprite, float sizeX, float sizeY, int hitPoint, GameCharacter gameCharacterRef) {
        super(sprite, sizeX, sizeY);
        this.hitPoint = hitPoint;
        this.gameCharacter = gameCharacterRef;
    }

    @Override
    public void act(float delta){
        super.act(delta);
        time -= delta;
        if (time < 0) {
            setToDelete();
        }
    }
    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint){
        int temp = this.hitPoint - hitPoint;
        if(temp > 0) setToDelete();
        else this.hitPoint = temp;
    }

    public GameCharacter getGameCharacter() {
        return gameCharacter;
    }
}
