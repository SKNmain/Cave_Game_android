package com.epiklp.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.epiklp.game.Cave;
import com.epiklp.game.functionals.Assets;
import com.epiklp.game.functionals.OwnSound;

/**
 * Created by epiklp on 21.02.18.
 */

public class Pause extends Stage {
    private final float BUTTON_SIZE = 128;
    private Image buttonResume,  buttonPlayMusic, buttonPlayEffects, buttonExit, buttonBox;
    private Table tablePlayer, tableDev;
    private boolean menuButton=false;
    private Label textResume, textMusic, textEffects, textExit, textBox;
    private Label.LabelStyle labelStyle;

    public Pause(boolean dev)
    {
        tablePlayer = new Table();
        buttonResume = new Image(Assets.manager.get(Assets.resume));
        buttonResume.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Cave.state = Cave.STATE.GAME;
            }
        });
        buttonPlayMusic = new Image(Assets.manager.get(Assets.on));
        buttonPlayMusic.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                OwnSound.MUSIC = !OwnSound.MUSIC;
                OwnSound.update("menuMusic");
            }
        });
        buttonPlayEffects = new Image(Assets.manager.get(Assets.on));
        buttonPlayEffects.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                OwnSound.EFFECTS = !OwnSound.EFFECTS;
            }
        });

        buttonExit = new Image(Assets.manager.get(Assets.exit));
        buttonExit.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                menuButton = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                menuButton = false;
            }
        });


        tablePlayer.setPosition(Cave.WIDTH/2, Cave.HEIGHT/2);
        tablePlayer.center().bottom();
        tablePlayer.add(buttonResume).size(BUTTON_SIZE, BUTTON_SIZE).padRight(10);
        tablePlayer.padRight(10);
        tablePlayer.add(buttonPlayMusic).size(BUTTON_SIZE, BUTTON_SIZE).padRight(10);
        tablePlayer.padRight(10);
        tablePlayer.add(buttonPlayEffects).size(BUTTON_SIZE, BUTTON_SIZE).padRight(10);
        tablePlayer.padRight(10);
        tablePlayer.add(buttonExit).size(BUTTON_SIZE, BUTTON_SIZE);
        tablePlayer.padRight(10);
        addActor(tablePlayer);
        if(dev == true)
        {
            tableDev = new Table();
            tableDev.setPosition(Cave.WIDTH/2, Cave.HEIGHT/2 - BUTTON_SIZE);
            tableDev.bottom().center();

            buttonBox = new Image(Assets.manager.get(Assets.render));
            buttonBox.addListener(new ClickListener()
            {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Cave.renderBox2D = !Cave.renderBox2D;
                }
            });
            tableDev.add(buttonBox).size(BUTTON_SIZE, BUTTON_SIZE);;
            addActor(tableDev);
        }
    }

    public boolean getMenuButton() {
        return menuButton;
    }
}
