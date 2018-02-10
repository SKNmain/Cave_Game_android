package com.epiklp.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.epiklp.game.Functional.Assets;
import com.epiklp.game.Functional.JsonFunction;
import com.epiklp.game.Functional.Sound;
import com.epiklp.game.Functional.TheBox;
import com.epiklp.game.Game.GameScreen;
import com.epiklp.game.Game.Logo;


public class Cave extends Game {
    public static boolean MUSIC = true;
    public static boolean EFFECTS = true;
    public static String LANGUAGE = "en";
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int PPM = 32;
    public static final float SCALE = 2;
    public static FileHandle FILE;

    @Override
    public void create() {



        Assets.load();
        //Assets.manager.finishLoading();
        //this.setScreen(new Logo(this));
        //z tego bedzie mozna zrobic pasek ładowania gdy bedzie wincej assetów
        while (!Assets.manager.update()) {
            System.out.println(Assets.manager.getProgress() * 100 + "%");
        }
        Sound.initialize();
        JsonFunction.initzialie();
        FILE = Gdx.files.local("option.json");
        if(!FILE.exists())
            JsonFunction.Create();
        else
            JsonFunction.Read();
        Sound.play("menuMusic");

        //Create physics
        TheBox.initWorld();


        this.setScreen(new Logo(this));
        //this.setScreen(new Menu(this));
        //this.setScreen(new GameScreen(this));

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        super.dispose();
        Assets.dispose();
    }
}
