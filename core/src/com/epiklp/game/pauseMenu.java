package com.epiklp.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Created by epiklp on 14.12.17.
 */

public class pauseMenu {

    private Stage stage;
    private Image pauseWindow, buttonExit, buttonRestart, buttonResume;
    public boolean pressExit, pressRestart, pressMenu, presssResume;

    public pauseMenu()
    {
        stage = new Stage();
        //Gdx.input.setInputProcessor(stage);
        pauseWindow = new Image(Assets.manager.get(Assets.pauseWindow));
        pressExit = pressRestart = pressMenu = presssResume = false;
        buttonExit = new Image(Assets.manager.get(Assets.quitButton));
        buttonExit.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pressExit = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                pressExit = false;
            }
        });
        buttonExit.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);

        stage.addActor(pauseWindow);
        stage.addActor(buttonExit);
    }

    public void draw()
    {
        stage.act();
        stage.draw();
    }

    public void dispose()
    {
        stage.dispose();
    }

}
