package com.epiklp.game;

import com.badlogic.gdx.Game;



public class Cave extends Game{
    public static final int WIDTH   = 1280;
    public static final int HEIGHT  = 720;
    public static final int PPM     = 32;
    public static final float SCALE = 2;



    @Override
    public void create() {
        this.setScreen(new Menu(this));
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
