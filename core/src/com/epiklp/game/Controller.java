package com.epiklp.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * Created by epiklp on 18.11.17.
 */

public class Controller implements Interface {
    private static final int BUTTON_SIZE = 10;
    private Stage stage;
    private boolean upPressed, attackPressed, leftPressed, rightPressed;
    private Table tabr;

    public Controller() {
        stage = new Stage(new ExtendViewport(width, height));
        Gdx.input.setInputProcessor(stage);

        Table tabl = new Table();
        tabl.bottom().left();


        Image leftButt = new Image(new Texture("button/Left.png"));
        leftButt.setSize(width / BUTTON_SIZE, width / BUTTON_SIZE);
        leftButt.addListener(new InputListener() {

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
        Image rightButt = new Image(new Texture("button/Right.png"));
        rightButt.setSize(width / BUTTON_SIZE, width / BUTTON_SIZE);
        rightButt.addListener(new InputListener() {

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
        tabl.add(rightButt).size(leftButt.getWidth(), leftButt.getHeight());
        tabl.row().padBottom(10);
        tabl.add(leftButt).size(leftButt.getWidth(), leftButt.getHeight());
        stage.addActor(tabl);

        tabr = new Table();
        tabr.setPosition(width - (width / BUTTON_SIZE) / 2, (width / BUTTON_SIZE));
        Image upButt = new Image(new Texture("button/Up.png"));
        upButt.setSize(width / BUTTON_SIZE, width / BUTTON_SIZE);
        upButt.addListener(new InputListener() {

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

        Image attackButt = new Image(new Texture("button/Attack.png"));
        attackButt.setSize(width / BUTTON_SIZE, width / BUTTON_SIZE);
        attackButt.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                attackPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                attackPressed = false;
            }
        });

        tabr.add(attackButt).size(leftButt.getWidth(), leftButt.getHeight());
        tabr.row().padBottom(10);
        tabr.add(upButt).size(leftButt.getWidth(), leftButt.getHeight());
        stage.addActor(tabr);

        setTableDebugMode(tabl);
        setTableDebugMode(tabr);
    }

    private void setTableDebugMode(Table tabl) {
        tabl.setDebug(true);
    }

    public void draw() {
        stage.act();
        stage.draw();
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isAttackPressed() {
        return attackPressed;
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
