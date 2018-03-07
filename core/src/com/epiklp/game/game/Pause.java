package com.epiklp.game.game;

import com.badlogic.gdx.graphics.Color;
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
    private Image buttonResume,  buttonPlayMusic, buttonPlayEffects, buttonMenu, buttonBox,
            buttonRestart, imageOn, imageOff;
    private Table tablePlayer, tableDev;
    private boolean restartButton = false;
    private boolean menuButton = false;
    private Label textResume, textMusic, textEffects, textMenu, textBox;
    private Label.LabelStyle labelStyle;

    public Pause(boolean dev)
    {
        imageOn = new Image(Assets.MANAGER.get(Assets.on));
        imageOff = new Image(Assets.MANAGER.get(Assets.off));
        labelStyle = new Label.LabelStyle(Assets.Font, Color.WHITE);
        textResume = new Label("RESUME", labelStyle);
        textMusic = new Label("MUSIC", labelStyle);
        textEffects = new Label("EFFECTS", labelStyle);
        textMenu = new Label("MENU", labelStyle);
        tablePlayer = new Table();
        buttonResume = new Image(Assets.MANAGER.get(Assets.resume));
        buttonResume.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Cave.state = Cave.STATE.GAME;
            }
        });
        if(OwnSound.MUSIC)
            buttonPlayMusic = imageOn;
        else
            buttonPlayMusic = imageOff;
        buttonPlayMusic.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(OwnSound.MUSIC) {
                    OwnSound.stop();
                    buttonPlayMusic = imageOff;
                }
                else {
                    OwnSound.play();
                    buttonPlayMusic = imageOn;
                }
                OwnSound.MUSIC = !OwnSound.MUSIC;
            }
        });
        buttonPlayEffects = new Image(Assets.MANAGER.get(Assets.on));
        buttonPlayEffects.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                OwnSound.EFFECTS = !OwnSound.EFFECTS;
            }
        });

        buttonMenu = new Image(Assets.MANAGER.get(Assets.exit));
        buttonMenu.addListener(new InputListener() {
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
        tablePlayer.add(buttonPlayMusic).size(BUTTON_SIZE, BUTTON_SIZE).padRight(10);
        tablePlayer.add(buttonPlayEffects).size(BUTTON_SIZE, BUTTON_SIZE).padRight(10);
        tablePlayer.add(buttonMenu).size(BUTTON_SIZE, BUTTON_SIZE);
        tablePlayer.row();
        tablePlayer.add(textResume).padRight(10);
        tablePlayer.add(textMusic).padRight(10);
        tablePlayer.add(textEffects).padRight(10);
        tablePlayer.add(textMenu).padRight(10);

        addActor(tablePlayer);
        if(dev == true)
        {
            tableDev = new Table();
            tableDev.setPosition(Cave.WIDTH/2, Cave.HEIGHT/2 - BUTTON_SIZE);
            tableDev.bottom().center();

            buttonBox = new Image(Assets.MANAGER.get(Assets.render));
            buttonBox.addListener(new ClickListener()
            {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Cave.renderBox2D = !Cave.renderBox2D;
                }
            });
            buttonRestart = new Image(Assets.MANAGER.get(Assets.restart));
            buttonRestart.addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    restartButton = true;
                    return true;
                }

                @Override
                public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                    restartButton = false;
                }
            });

            tableDev.add(buttonBox).size(BUTTON_SIZE, BUTTON_SIZE).padRight(10);
            tableDev.add(buttonRestart).size(BUTTON_SIZE,BUTTON_SIZE).padRight(10);
            addActor(tableDev);
        }
    }

    public boolean getMenuButton() {
        return menuButton;
    }
    public boolean getRestartButton(){return restartButton;}
}
