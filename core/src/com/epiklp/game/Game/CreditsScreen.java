package com.epiklp.game.Game;

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
import com.epiklp.game.Functional.Assets;
import com.epiklp.game.TextureGame;


class CreditsScreen extends Stage {
    final Cave cave;
    private TextureGame textMenu;
    private Stage stage;
    private BitmapFont bitmapFont;
    private Label text;
    private Label.LabelStyle labelStyle;
    private Image backImage;
    private Boolean backPress;

    public CreditsScreen(Cave cave) {
        this.cave = cave;
        textMenu = new TextureGame();
        bitmapFont = new BitmapFont();
        labelStyle = new Label.LabelStyle(Assets.Font, Color.WHITE);
        text = new Label("Autorzy:\n Rafal Rybowski\n Kamil Sykula", labelStyle);
        text.setPosition(0, Cave.HEIGHT / 2);
        addActor(text);
        backPress = false;
        backImage = new Image(new Texture("MenuButtons/button_back.png"));
        backImage.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                backPress = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                backPress = false;
            }

        });
        backImage.setPosition(Cave.WIDTH - backImage.getWidth(), 0);
        addActor(backImage);
    }


    @Override
    public void dispose() {
        textMenu.dispose();
    }
}
