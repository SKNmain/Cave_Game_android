package com.epiklp.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.epiklp.game.functionals.Assets;
import com.epiklp.game.functionals.Controller;
import com.epiklp.game.functionals.JsonFunction;
import com.epiklp.game.functionals.OwnSound;
import com.epiklp.game.functionals.UI;
import com.epiklp.game.functionals.b2d.TheBox;
import com.epiklp.game.game.BossLevel;
import com.epiklp.game.game.GameLevel;
import com.epiklp.game.game.Logo;
import com.epiklp.game.game.Pause;


public class Cave extends Game {
    public enum STATE {
        OPTION, GAME, CREDIT, SHOP, RESTART
    }

    public enum CaveGame{
        GAME, MENU
    }
    public static CaveGame CaveState = CaveGame.MENU;
    public static STATE state = STATE.GAME;
    public static String LANGUAGE = "en";
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    public static final int PPM = 32;
    public static final float SCALE = 2;
    public static FileHandle FILE;
    public static UI ui;
    public static boolean renderBox2D, change;
    public static Pause menuPause;
    public static Controller controller;
    public static OrthographicCamera camera;
    public static Viewport viewport;
    @Override
    public void create() {
        camera = new OrthographicCamera(Cave.WIDTH, Cave.HEIGHT);
        viewport = new ExtendViewport(Cave.WIDTH, Cave.HEIGHT, camera);
        change = true;
        renderBox2D = false;
        Assets.load();
        //Assets.MANAGER.finishLoading();
        //this.setScreen(new Logo(this));
        //z tego bedzie mozna zrobic pasek ładowania gdy bedzie wincej assetów
        while (!Assets.MANAGER.update()) {
            System.out.println(Assets.MANAGER.getProgress() * 100 + "%");
        }
        OwnSound.initialize();
        JsonFunction.initzialie();
        FILE = Gdx.files.local("option.json");
        if (!FILE.exists())
            JsonFunction.Create();
        else
            JsonFunction.Read();
        JsonFunction.edit("Music");
        OwnSound.firstPlay();
        JsonFunction.updateJSON();

        ui = new UI();
        //Create physics
        TheBox.initWorld();
        menuPause = new Pause(true);
        controller = null;
        this.setScreen(new Logo(this));
        //this.setScreen(new Menu(this));
        //this.setScreen(new GameLevel(this));
        //this.setScreen(new BossLevel(this));

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void render() {
        /*if(change) {
            if (CaveState.equals(CaveGame.LOGO)) {
                this.setScreen(logo);
            } else if (CaveState.equals(CaveGame.MENU)) {
                this.setScreen(menu);
            } else if (CaveState.equals(CaveGame.GAME)) {
                this.setScreen(gameScreen);
            }
            change = false;
        }*/
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
        controller.dispose();
        menuPause.dispose();
        ui.dispose();
        Assets.dispose();
        super.dispose();
    }
}
