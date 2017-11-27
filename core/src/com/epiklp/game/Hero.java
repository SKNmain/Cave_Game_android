package com.epiklp.game;


public class Hero implements Interface {

    private int live;
    private int magic;
    private float speedWalk;
    private boolean ground = false;

    public Hero()
    {
        this.live = Interface.live;
        this.magic = Interface.magic;
        this.speedWalk = Interface.speedWalk;
    }

    public int getLive() {
        return live;
    }

    public int getMagic() {
        return magic;
    }

    public float getspeedWalk() {
        return speedWalk+live*0.03f;
    }

    public boolean getGround() {
        return ground;
    }

    public void setLive(int live) {
        this.live += live;
    }

    public void setMagic(int magic) {
        this.magic += magic;
    }

    public void setGround(boolean ground) {
        this.ground = ground;
    }

}
