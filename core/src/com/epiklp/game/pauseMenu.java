package com.epiklp.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * Created by epiklp on 14.12.17.
 */

public class pauseMenu extends Stage {

    private Image pauseWindow, buttonExit, buttonRestart, buttonResume;
    public boolean pressExit, pressRestart, presssResume;
    private Table tbl;

    public pauseMenu()
    {
        pauseWindow = new Image(Assets.manager.get(Assets.pauseWindow));
        pressExit = pressRestart = presssResume = false;
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

        buttonResume = new Image(Assets.manager.get(Assets.resumeButton));
        buttonResume.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                presssResume = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                presssResume = false;
            }
        });


        buttonRestart = new Image(Assets.manager.get(Assets.restartButton));
        buttonRestart.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pressRestart = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                pressRestart = false;
            }
        });

        tbl = new Table();
        tbl.setPosition(Cave.WIDTH/2, Cave.HEIGHT/2);
        tbl.row().padBottom(10);
        tbl.add(buttonResume).size(buttonResume.getWidth(), buttonResume.getHeight());
        tbl.row().padBottom(10);
        tbl.add(buttonRestart).size(buttonResume.getWidth(), buttonResume.getHeight());
        tbl.row().padBottom(10);
        tbl.add(buttonExit).size(buttonResume.getWidth(), buttonResume.getHeight());

        addActor(pauseWindow);
        addActor(tbl);
    }


}
