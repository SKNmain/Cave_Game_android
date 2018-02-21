package com.epiklp.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.epiklp.game.Cave;
import com.epiklp.game.functionals.Assets;
import com.epiklp.game.functionals.Sound;

/**
 * Created by epiklp on 21.02.18.
 */

public class Pause extends Stage {
    private final float BUTTON_SIZE = 128;
    private Image buttonResume,  buttonPlayMusic, buttonPlayEffects, buttonExit, buttonBox;
    private Table table;
    private Label textResume, textMusic, textEffects, textExit, textBox;
    private Label.LabelStyle labelStyle;

    public Pause()
    {
        table = new Table();
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
                Sound.MUSIC = !Sound.MUSIC;
                Sound.update("menuMusic");
            }
        });
        buttonPlayEffects = new Image(Assets.manager.get(Assets.on));
        buttonPlayEffects.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Sound.EFFECTS = !Sound.EFFECTS;
                Sound.update("menuMusic");
            }
        });
        buttonExit = new Image(Assets.manager.get(Assets.exit));
        buttonExit.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        buttonBox = new Image(Assets.manager.get(Assets.render));
        buttonBox.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Cave.renderBox2D = !Cave.renderBox2D;
            }
        });

        table.setPosition(Cave.WIDTH/2, Cave.HEIGHT/2);
        table.center().bottom();
        table.padRight(10);
        table.add(buttonResume).size(BUTTON_SIZE, BUTTON_SIZE);
        table.padRight(10);
        table.add(buttonPlayMusic).size(BUTTON_SIZE, BUTTON_SIZE);
        table.padRight(10);
        table.add(buttonPlayEffects).size(BUTTON_SIZE, BUTTON_SIZE);
        table.padRight(10);
        table.add(buttonExit).size(BUTTON_SIZE, BUTTON_SIZE);
        table.padRight(10);
        table.add(buttonBox).size(BUTTON_SIZE, BUTTON_SIZE);
        table.row();
        addActor(table);
    }
}
