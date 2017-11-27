package com.epiklp.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;


public class menuButton extends InputListener{
    private Stage stage;
    private Image playImage, creditImage, continueImage;
    private Boolean playPress, creditPress, continuePress;
    private Table tbr;

    public menuButton()
    {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        playPress = creditPress = continuePress = false;
        playImage = new Image(new Texture("MenuButtons/button_play.png"));
        playImage.addListener(new InputListener(){
            @Override
                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                playPress = true;
                                return true;
                            }

                            @Override
                            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                                playPress = false;
                            }
        });
        continueImage = new Image(new Texture("MenuButtons/button_continue.png"));
        continueImage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                continuePress = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                continuePress = false;
            }
        });
        creditImage = new Image(new Texture("MenuButtons/button_credit.png"));
        creditImage.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                creditPress = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                creditPress = false;
            }
        });

        tbr = new Table();
        tbr.bottom().left();
        tbr.row().padBottom(10);
        tbr.add(playImage).size(playImage.getWidth(), playImage.getHeight());
        tbr.row().padBottom(10);
        tbr.add(continueImage).size(playImage.getWidth(), playImage.getHeight());
        tbr.row().padBottom(10);
        tbr.add(creditImage).size(playImage.getWidth(), playImage.getHeight());

        stage.addActor(tbr);

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

    public Boolean getPlayPress() {
        return playPress;
    }

    public Boolean getCreditPress() {
        return creditPress;
    }

    public Boolean getContinuePress() {
        return continuePress;
    }
}
