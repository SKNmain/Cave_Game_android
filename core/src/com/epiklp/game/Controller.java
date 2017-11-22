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
    private static final int BUTTON_SIZE = width/10;
    private Stage stage;
    private boolean upPressed, attackPressed, leftPressed, rightPressed;

    public Controller() {
        stage = new Stage(new ExtendViewport(width, height));
        Gdx.input.setInputProcessor(stage);

        Table tableLeft = new Table();
        Table tableRight = new Table();

        initLeftSide(tableLeft);
        initRightSide(tableRight);

        setTableDebugMode(tableLeft);
        setTableDebugMode(tableRight);
    }

    private void setTableDebugMode(Table table) {
        table.setDebug(true);
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

    private void initLeftSide(Table tableLeft) {
        tableLeft.bottom().left();
        Image leftButt = new Image(new Texture("button/Left.png"));
        leftButt.setSize(BUTTON_SIZE, BUTTON_SIZE);
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
        rightButt.setSize(BUTTON_SIZE, BUTTON_SIZE);
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
        tableLeft.columnDefaults(1).width(BUTTON_SIZE);
        tableLeft.add(leftButt).size(BUTTON_SIZE, BUTTON_SIZE);
        tableLeft.add(rightButt).size(BUTTON_SIZE, BUTTON_SIZE);

        stage.addActor(tableLeft);
    }

    private void initRightSide(Table tableRight) {
        tableRight.setPosition(width - (BUTTON_SIZE) / 2, (BUTTON_SIZE));
        Image upButt = new Image(new Texture("button/Up.png"));
        upButt.setSize(BUTTON_SIZE, BUTTON_SIZE);
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
        attackButt.setSize(BUTTON_SIZE, BUTTON_SIZE);
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

        tableRight.add(attackButt).size(BUTTON_SIZE, BUTTON_SIZE);
        tableRight.row().padBottom(10);
        tableRight.add(upButt).size(BUTTON_SIZE, BUTTON_SIZE);

        stage.addActor(tableRight);
    }
}
