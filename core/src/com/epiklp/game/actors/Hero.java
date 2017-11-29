package com.epiklp.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.epiklp.game.Assets;
import com.epiklp.game.TheBox;

/**
 * Created by epiklp on 14.11.17.
 */

public class Hero extends GameActor {

    private int magic;
    private boolean ground = false;

    public Hero() {
        super(new Sprite(Assets.manager.get(Assets.player)));
        setBody(TheBox.createBox(400, 300,28f , 48, false));

        this.life = 100;
        this.magic = 100;
        this.speedWalk = 3f;
    }

    public Hero(Sprite sprite, int life, int magic, float speedWalk)
    {
        super(sprite);
        this.life = life;
        this.magic = magic;
        this.speedWalk = speedWalk;
    }

    public int getMagic() {
        return magic;
    }

    @Override
    public float getSpeedWalk() {
        return speedWalk+life*0.03f;
    }

    public boolean getGround() {
        return ground;
    }

    public void setMagic(int magic) {
        this.magic += magic;
    }

    public void setGround(boolean ground) {
        this.ground = ground;
    }

    public void setSpeedX(float speedX){
        body.setLinearVelocity(speedX, body.getLinearVelocity().y);
    }
    public void setSpeedY(float speedY){
        body.setLinearVelocity(body.getLinearVelocity().x, speedY);
    }
}
