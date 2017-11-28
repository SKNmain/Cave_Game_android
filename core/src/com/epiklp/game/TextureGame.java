package com.epiklp.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * Created by epiklp on 18.11.17.
 *
 *  ParallaxBackground is a bit unoptimalized - it use a 9 texture.
 *
 */

public class TextureGame{
    private Stage stage;
    private Image[] layerImage;
    private ParallaxBackground parallaxBackground;
    public TextureGame()
    {
        stage = new Stage(new ExtendViewport(Cave.WIDTH, Cave.HEIGHT));
        Gdx.input.setInputProcessor(stage);
        String tmp = "";
        layerImage = new Image[9];
        Array<Texture> textures = new Array<Texture>();
        for(int i=0; i<9; i++) {
            tmp = "layer/" + (i+1) + ".png";
            textures.add(new Texture(Gdx.files.internal(tmp)));
            layerImage[i] = new Image(textures.get(i));
            layerImage[i].setWidth(Cave.WIDTH);
            layerImage[i].setHeight(Cave.HEIGHT);
           // stage.addActor(layerImage[i]);
        }
        parallaxBackground = new ParallaxBackground(textures);
        parallaxBackground.setSize(Cave.WIDTH, Cave.HEIGHT);
        parallaxBackground.setSpeed(1);
        stage.addActor(parallaxBackground);
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
