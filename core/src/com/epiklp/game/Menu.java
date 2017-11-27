package com.epiklp.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;


/**
 * Created by epiklp on 22.11.17.
 */

class Menu implements Screen {
    final Cave cave;
    private TextureGame textMenu;
    private menuButton menu;

    public Menu(final Cave cave) {
        this.cave = cave;

        textMenu = new TextureGame();
        menu = new menuButton();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime());
        textMenu.draw();
        menu.draw();

    }

    private void update(float delta)
    {
        if(menu.getPlayPress())
        {
            cave.setScreen(new GameScrean(cave));
            dispose();
        }
        if(menu.getContinuePress())
        {

        }
        if(menu.getCreditPress())
        {

        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        textMenu.dispose();
        menu.dispose();
    }
}
