package com.epiklp.game.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.epiklp.game.Cave;
import com.epiklp.game.functionals.Assets;


class CreditsScreen extends Stage {
    private Label text;
    private Label.LabelStyle labelStyle;
    private Image backImage, Layer;
    private Boolean backPress;

    public CreditsScreen() {
        labelStyle = new Label.LabelStyle(Assets.Font, Color.WHITE);
        text = new Label("Autors:\nRafal Rybowski\nKamil Sykula", labelStyle);
        text.setPosition(0, Cave.HEIGHT / 2);
        text.setFontScale(4.5f);
        Layer = new Image(Assets.MANAGER.get(Assets.menuLayer));
        Layer.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        addActor(Layer);
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
        backImage.setPosition(Cave.WIDTH/2 - backImage.getWidth()/2, 0);
        addActor(backImage);
    }


    public boolean isBackPress() {return backPress;}


    @Override
    public void dispose() {

    }
}
