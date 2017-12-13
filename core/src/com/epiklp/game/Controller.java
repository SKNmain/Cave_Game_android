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
    private boolean upPressed, atackPressed, leftPressed, rightPressed, resetPressed;

    public Controller() {
        stage = new Stage(new ExtendViewport(Cave.WIDTH, Cave.HEIGHT));
        Gdx.input.setInputProcessor(stage);


        Table tabTopLeft = new Table();
        tabTopLeft.top().right();
        Image imuR = new Image(Assets.manager.get(Assets.upButton));
        imuR.setSize(imuR.getWidth(), imuR.getWidth());
        imuR.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                resetPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                resetPressed = false;
            }
        });
        tabTopLeft.debug();
        tabTopLeft.add(imuR).size(BUTTON_SIZE, BUTTON_SIZE);
        stage.addActor(tabTopLeft);

        Table tabl = new Table();
        tabl.bottom().left();
        Image imu = new Image(Assets.manager.get(Assets.upButton));
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

        Image iml = new Image(Assets.manager.get(Assets.leftButton));
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

        Table tabr = new Table();
        tabr.setPosition(Cave.WIDTH - (Cave.WIDTH / 10) / 2, (Cave.WIDTH / 10));
        Image imr = new Image(Assets.manager.get(Assets.rightButton));
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

        Image ime = new Image(Assets.manager.get(Assets.rightButton));
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

    public boolean isResetPressed() {
        return resetPressed;
    }

    public void dispose() {
        stage.dispose();
    }
}
