package com.epiklp.game.actors;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.epiklp.game.Assets;
import com.epiklp.game.Cave;
import com.epiklp.game.TheBox;

/**
 * Created by epiklp on 14.11.17.
 */

public class Hero extends GameActor {

    private int magic;
    private boolean ground = false;


    public Hero() {
        super(new Sprite(Assets.manager.get(Assets.player)));
        sprite.setSize(108 / Cave.SCALE, 192 / Cave.SCALE);
        sprite.setOrigin(108 / 2, 192 / 2);
        setBody(TheBox.createBox(400, 300, 28f, 48, false));
        body.setUserData(this);

        initStats();
    }

    @Override
    public void initStats() {
        this.life        = 100;
        this.magic        = 100;
        this.attackSpeed = 2;
        this.speedWalk   = 3f;
        this.strengh     = 10;
    }

    @Override
    public float getSpeedWalk() {
        return speedWalk + life * 0.03f;
    }

    public int getMagic() {
        return magic;
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

    public void setSpeedX(float speedX) {
        body.setLinearVelocity(speedX, body.getLinearVelocity().y);
    }

    public void setSpeedY(float speedY) {
        body.setLinearVelocity(0, speedY);
    }
}
