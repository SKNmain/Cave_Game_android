package com.epiklp.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * Created by epiklp on 18.11.17.
 */

public class TextureGame implements Interface {
    private Stage stage;
    private Image[] LayerImage;
    public TextureGame()
    {

        stage = new Stage(new ExtendViewport(width, height));
        Gdx.input.setInputProcessor(stage);
        String tmp = "";
        LayerImage = new Image[9];
        for(int i=0; i<9; i++) {
            tmp = "layer/" + (i+1) + ".png";
            LayerImage[i] = new Image(new Texture(Gdx.files.internal(tmp)));
            LayerImage[i].setWidth(width);
            LayerImage[i].setHeight(height);
            stage.addActor(LayerImage[i]);
        }
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
