package com.epiklp.game.Functional;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ArrayMap;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by epiklp on 29.11.17.
 */

public class Assets {

    //Asset
    public static final AssetManager manager = new AssetManager();
    public static final AssetDescriptor<Texture> logo = new AssetDescriptor<Texture>("logo.png", Texture.class);

    //player
    /*******************************************/
    public static final AssetDescriptor<Texture> player = new AssetDescriptor<Texture>("character/1.png", Texture.class);
    public static final AssetDescriptor<Texture> fireBall = new AssetDescriptor<Texture>("character/fireball.png", Texture.class);
    /*******************************************/

    //Enemy
    /*******************************************/
    public static final AssetDescriptor<Texture> flameDemon = new AssetDescriptor<Texture>("enemies/FlameDemon.png", Texture.class);
    /*******************************************/


    //Buttons
    /*******************************************/
    public static final AssetDescriptor<Texture> leftButton = new AssetDescriptor<Texture>("button/Left.png", Texture.class);
    public static final AssetDescriptor<Texture> rightButton = new AssetDescriptor<Texture>("button/Right.png", Texture.class);
    public static final AssetDescriptor<Texture> attackButton = new AssetDescriptor<Texture>("button/Attack.png", Texture.class);
    public static final AssetDescriptor<Texture> upButton = new AssetDescriptor<Texture>("button/Up.png", Texture.class);
    public static final AssetDescriptor<Texture> homeButton = new AssetDescriptor<Texture>("button/home2.png", Texture.class);
    public static final AssetDescriptor<Texture> goButton = new AssetDescriptor<Texture>("button/go.png", Texture.class);
    public static final AssetDescriptor<Texture> pauseWindow = new AssetDescriptor<Texture>("PauseMenu/window.png", Texture.class);
    public static final AssetDescriptor<Texture> restartButton = new AssetDescriptor<Texture>("PauseMenu/button_restart.png", Texture.class);
    public static final AssetDescriptor<Texture> resumeButton = new AssetDescriptor<Texture>("PauseMenu/button_resume.png", Texture.class);
    public static final AssetDescriptor<Texture> backButton = new AssetDescriptor<Texture>("MenuButtons/button_back.png", Texture.class);
    public static final AssetDescriptor<Texture> playButton = new AssetDescriptor<Texture>("MenuButtons/button_play.png", Texture.class);
    public static final AssetDescriptor<Texture> quitButton = new AssetDescriptor<Texture>("MenuButtons/button_quit.png", Texture.class);
    public static final AssetDescriptor<Texture> continueButton = new AssetDescriptor<Texture>("MenuButtons/button_continue.png", Texture.class);
    public static final AssetDescriptor<Texture> creditButton = new AssetDescriptor<Texture>("MenuButtons/button_credit.png", Texture.class);
    public static final AssetDescriptor<Texture> enterButton = new AssetDescriptor<Texture>("button/E.png", Texture.class);

    /*******************************************/

    //menu
    /*******************************************/
    public static final AssetDescriptor<Texture> menuMap = new AssetDescriptor<Texture>("Map/menu_map.png", Texture.class);
    public static final AssetDescriptor<Music> menuMusic = new AssetDescriptor<Music>("sounds/menu.wav", Music.class);
    public static final AssetDescriptor<Texture> menuLayer = new AssetDescriptor<Texture>("menu/all.png", Texture.class);
    public static final AssetDescriptor<Texture> shopLayer = new AssetDescriptor<Texture>("menu/shop.png", Texture.class);
    public static final AssetDescriptor<Texture> creditLayer = new AssetDescriptor<Texture>("menu/credit.png", Texture.class);
    public static final AssetDescriptor<Texture> cave1Layer = new AssetDescriptor<Texture>("menu/cave1.png", Texture.class);
    public static final AssetDescriptor<Texture> cave2Layer = new AssetDescriptor<Texture>("menu/cave2.png", Texture.class);
    /*******************************************/

    public static BitmapFont Font; //Font

    public static final AssetDescriptor<Texture>[] layer = new AssetDescriptor[9]; // GameOver

    public static void load() {

        manager.load(logo);


        manager.load(player);
        manager.load(flameDemon);
        manager.load(fireBall);

        //Buttons
        manager.load(restartButton);
        manager.load(resumeButton);
        manager.load(leftButton);
        manager.load(rightButton);
        manager.load(upButton);
        manager.load(attackButton);
        manager.load(homeButton);
        manager.load(goButton);
        manager.load(pauseWindow);
        manager.load(backButton);
        manager.load(quitButton);
        manager.load(playButton);
        manager.load(continueButton);
        manager.load(creditButton);
        manager.load(enterButton);

        //Menu
        manager.load(menuLayer);
        manager.load(shopLayer);
        manager.load(creditLayer);
        manager.load(cave1Layer);
        manager.load(cave2Layer);
        manager.load(menuMap);
        manager.load(menuMusic);


        String tmp = "";
        for (int i = 0; i < 9; i++) {
            tmp = "layer/" + (i + 1) + ".png";
            layer[i] = new AssetDescriptor<Texture>(tmp, Texture.class);
            manager.load(layer[i]);
        }


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/font.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 80;
        parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
        BitmapFont character = generator.generateFont(parameter);
        Font = character;

    }

    public static void dispose() {
        manager.dispose();
    }
}
