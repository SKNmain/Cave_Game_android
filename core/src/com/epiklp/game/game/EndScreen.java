package com.epiklp.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.epiklp.game.Cave;
import com.epiklp.game.functionals.Assets;
import com.epiklp.game.TextureGame;
import com.epiklp.game.functionals.OwnSound;

/**
 * Created by Asmei on 2017-11-27.
 */

public class EndScreen implements Screen {
    final Cave cave;
    private Image Layer;
    //private TextureGame textMenu;
    private Stage stage;
    private Label text;
    private Label.LabelStyle labelStyle;
    private Image backImage;
    private Boolean backPress;

    public EndScreen(Cave cave, boolean win) {
        this.cave = cave;


        //textMenu = new TextureGame();
        Layer = new Image(Assets.MANAGER.get(Assets.creditLayer));
        Layer.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage();
        stage.addActor(Layer);
        Gdx.input.setInputProcessor(stage);
        labelStyle = new Label.LabelStyle(Assets.bigFont, Color.WHITE);
        if(win) {
            text = new Label("Congratulation\n You Win!!!", labelStyle);
        } else {
            text = new Label("GAME OVER!", labelStyle);
        }
        text.setPosition(0, Cave.HEIGHT / 2);
        stage.addActor(text);
        backPress = false;
        backImage = new Image(new Texture("MenuButtons/button_quit.png"));
        backImage.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                backPress = true;
                return true;
            }
        });
        backImage.setPosition(Cave.WIDTH - backImage.getWidth(), 0);
        stage.addActor(backImage);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        if (backPress == true) {
            cave.setScreen(new Menu(cave));
            OwnSound.stop();
            dispose();
            Cave.CaveState = Cave.CaveGame.MENU;
            Cave.state = Cave.STATE.GAME;
            if(OwnSound.MUSIC)
                OwnSound.play();
        }
        //textMenu.draw();
        stage.act();
        stage.draw();
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
        //textMenu.dispose();
        stage.dispose();
    }
}
