package com.epiklp.game.functionals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.epiklp.game.Cave;


public class Controller extends Stage {
    private final float BUTTON_SIZE = 128;
    private boolean upPressed, atackPressed, leftPressed, rightPressed, homePresed, sellectPresed;
    private Table tabRight, tabLeft;
    private Image buttonRight, buttonLeft, buttonUp, buttonAttack, buttonHome, sellectButton, changeButton;
    private TextureRegionDrawable swordTexture, mageTexture;
    public boolean weapon; //true - sword // false - mage

    public Controller(boolean menu) {
        weapon = true;
        //Buttons on Left
        /*******************************************/
        tabLeft = new Table();
        tabLeft.bottom().left();

        buttonRight = new Image(Assets.MANAGER.get(Assets.rightButton));
        buttonRight.setSize(BUTTON_SIZE, BUTTON_SIZE);
        buttonRight.addListener(new InputListener() {
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

        buttonLeft = new Image(Assets.MANAGER.get(Assets.leftButton));
        buttonLeft.setSize(BUTTON_SIZE, BUTTON_SIZE);
        buttonLeft.addListener(new InputListener() {
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
        if(!menu) {
            changeButton = new Image(Assets.MANAGER.get(Assets.changeButton));
            changeButton.setSize(BUTTON_SIZE, BUTTON_SIZE);
            changeButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    weapon = !weapon;
                    if (weapon) {
                        buttonAttack.setDrawable(swordTexture);
                    } else {
                        buttonAttack.setDrawable(mageTexture);
                    }
                }
            });
            tabLeft.add(changeButton).size(BUTTON_SIZE, BUTTON_SIZE);
            tabLeft.row();
            tabLeft.add(buttonLeft).size(BUTTON_SIZE, BUTTON_SIZE);
            tabLeft.add(buttonRight).size(BUTTON_SIZE, BUTTON_SIZE);
            addActor(tabLeft);
        }
        else {
            tabLeft.add(buttonLeft).size(BUTTON_SIZE, BUTTON_SIZE);
            tabLeft.add(buttonRight).size(BUTTON_SIZE, BUTTON_SIZE);
            addActor(tabLeft);
        }
        /*******************************************/

        //Buttons on Right
        /*******************************************/
        if (!menu) {
            swordTexture = new TextureRegionDrawable(new TextureRegion(Assets.MANAGER.get(Assets.swordAttackButton)));
            mageTexture = new TextureRegionDrawable(new TextureRegion(Assets.MANAGER.get(Assets.mageAttackButton)));
            tabRight = new Table();
            tabRight.setPosition(Gdx.graphics.getWidth() - 64, (Cave.WIDTH / 10));
            buttonUp = new Image(Assets.MANAGER.get(Assets.upButton));
            buttonUp.setSize(BUTTON_SIZE, BUTTON_SIZE);
            buttonUp.addListener(new InputListener() {
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

            if(weapon) {
                buttonAttack = new Image(swordTexture);
            }
            else {
                buttonAttack = new Image(mageTexture);
            }

            buttonAttack.setSize(BUTTON_SIZE, BUTTON_SIZE);
            buttonAttack.addListener(new InputListener() {
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
            tabRight.add(buttonAttack).size(BUTTON_SIZE, BUTTON_SIZE);
            tabRight.row().padBottom(10);
            tabRight.add(buttonUp).size(BUTTON_SIZE, BUTTON_SIZE);
            addActor(tabRight);
        }

        /*******************************************/


        //Pause/Menu
        /*******************************************/
        buttonHome = new Image(Assets.MANAGER.get(Assets.homeButton));
        buttonHome.setPosition(Gdx.graphics.getWidth() - 74, Gdx.graphics.getHeight() - 74);
        buttonHome.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                homePresed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                homePresed = false;
            }
        });
        addActor(buttonHome);
        /*******************************************/

        sellectButton = new Image(Assets.MANAGER.get(Assets.sellectButton));
        sellectButton.setSize(BUTTON_SIZE, BUTTON_SIZE);
        sellectButton.setPosition(Cave.WIDTH - BUTTON_SIZE, 0);

        sellectButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                sellectPresed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                sellectPresed = false;
            }
        });
    }

    public void enterOn() {
        addActor(sellectButton);
    }

    public void enterOff() {
        sellectButton.remove();
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isSellectPresed() {
        return sellectPresed;
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

    public boolean isHomePresed() {
        return homePresed;
    }

}
 