package com.epiklp.game;

import com.badlogic.gdx.Game;



public class Cave extends Game{
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
