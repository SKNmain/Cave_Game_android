package com.epiklp.game.actors;


/**
 * Created by epiklp on 14.11.17.
 */

public class Hero extends GameActor {

    private int magic;
    private boolean ground = false;

    public Hero() {
        this.life = 100;
        this.magic = 100;
        this.speedWalk = 3f;
    }

    public Hero(int life, int magic, float speedWalk)
    {
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

}
