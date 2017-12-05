package com.epiklp.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;


public class Controller {
    private static final float BUTTON_SIZE = Cave.WIDTH / 10;
    private Stage stage;
    private boolean upPressed, atackPressed, leftPressed, rightPressed;
    private Table tabr;

    public Controller() {
        stage = new Stage(new ExtendViewport(Cave.WIDTH, Cave.HEIGHT));
        Gdx.input.setInputProcessor(stage);

        Table tabl = new Table();
        tabl.bottom().left();
        Image imu = new Image(new Texture("button/Up.png"));
        imu.setSize(BUTTON_SIZE, BUTTON_SIZE);
        imu.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });

        Image iml = new Image(new Texture("button/Left.png"));
        iml.setSize(BUTTON_SIZE, BUTTON_SIZE);
        iml.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });
        tabl.add(imu).size(iml.getWidth(), iml.getHeight());
        tabl.row().padBottom(10);
        tabl.add(iml).size(iml.getWidth(), iml.getHeight());
        stage.addActor(tabl);

        tabr = new Table();
        tabr.setPosition(Cave.WIDTH - (Cave.WIDTH / 10) / 2, (Cave.WIDTH / 10));
        Image imr = new Image(new Texture("button/Right.png"));
        imr.setSize(BUTTON_SIZE, BUTTON_SIZE);
        imr.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        Image ime = new Image(new Texture("button/Attack.png"));
        ime.setSize(BUTTON_SIZE, BUTTON_SIZE);
        ime.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                atackPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                atackPressed = false;
            }
        });
        tabr.add(ime).size(iml.getWidth(), iml.getHeight());
        tabr.row().padBottom(10);
        tabr.add(imr).size(iml.getWidth(), iml.getHeight());
        stage.addActor(tabr);
    }

    public void draw() {
        stage.act();
        stage.draw();
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isAttackPressed() {
        return atackPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void dispose() {
        stage.dispose();
    }
}
