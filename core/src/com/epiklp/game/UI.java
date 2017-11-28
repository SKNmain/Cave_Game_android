package com.epiklp.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


public class UI {
    private BitmapFont bitmapFont;
    private Label text;
    private Label.LabelStyle labelStyle;
    private Stage stage;
    private Texture lifeTexture, magicTexture;
    private SpriteBatch batch;

    public UI()
    {
        batch      = new SpriteBatch();
        bitmapFont = new BitmapFont();
        labelStyle = new Label.LabelStyle(bitmapFont, Color.WHITE);
        text       = new Label("FPS:", labelStyle);
        stage      = new Stage();
        text.setPosition(0, Cave.HEIGHT-100);
        stage.addActor(text);
        lifeTexture  = CreateTexture(100,32,1,0,0,1);
        magicTexture = CreateTexture(100,32,0,0,1,1);
    }

    private Texture CreateTexture(int width, int hight, int r,int g,int b,int a)
    {
        Texture tmp;
        Pixmap pixmap = new Pixmap(width*4, hight, Pixmap.Format.RGB888);
        pixmap.setColor(r, g, b, 1);
        pixmap.fillRectangle(0, 0, width*4, hight);
        tmp = new Texture(pixmap);
        pixmap.dispose();
        return tmp;
    }

    public void draw(int life, int magic, float x, float y)
    {
        update(life, magic);
        batch.begin();
        batch.draw(lifeTexture,-1, Cave.HEIGHT - 64 ); // x = -1 is only temporary, to check if all texture will disapper if hero is dead
        batch.draw(magicTexture,-1, Cave.HEIGHT - 112 );
        batch.end();
        text.setText("FPS: " + Gdx.graphics.getFramesPerSecond() + "\n" + "x: " + x + " y: " + y);
        stage.act();
        stage.draw();
    }

    public void update(int life, int magic)
    {
        lifeTexture.dispose();
        magicTexture.dispose();
        if(life <= 0 ) life = 1;
        if(magic <= 0 ) magic = 1;
        lifeTexture  = CreateTexture(life, 32, 1,0,0,1);
        magicTexture = CreateTexture(magic,32,0,0,1,1);
    }

    public void dispose()
    {
        lifeTexture.dispose();
        magicTexture.dispose();
        stage.dispose();

    }

}
