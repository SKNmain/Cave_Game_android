package com.epiklp.game.functionals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.epiklp.game.Cave;


public class UI extends Stage {
    private BitmapFont bitmapFont;
    private Label text, lifeText, magicText, auraText;
    private Label.LabelStyle labelStyle;
    private Image lifeTexture, magicTexture, timeTexture;
    private Image statusBelt;
    private Image sword, staff;
    private int length;
    private boolean weapon;


    public UI() {
        bitmapFont = new BitmapFont();
        weapon = true;
        labelStyle = new Label.LabelStyle(bitmapFont, Color.WHITE);
        text = new Label("FPS:" + Gdx.graphics.getFramesPerSecond(), labelStyle);
        text.setPosition(0, Cave.HEIGHT /2);
        addActor(text);
        lifeTexture = new Image(Assets.manager.get(Assets.statusHp));
        lifeTexture.setPosition(219, Cave.HEIGHT - 69);
        lifeTexture.setScale(Cave.SCALE*1.5f);
        lifeText = new Label("HP: 0/0", labelStyle);
        lifeText.setPosition(219, Cave.HEIGHT - 69);
        magicTexture = new Image(Assets.manager.get(Assets.statusMana));
        magicTexture.setPosition(219, Cave.HEIGHT - 105);
        magicTexture.setScale(Cave.SCALE*1.5f);
        magicText = new Label("MP: 0/0", labelStyle);
        magicText.setPosition(219, Cave.HEIGHT - 105);
        timeTexture = new Image(Assets.manager.get(Assets.statusTime));
        timeTexture.setPosition(219, Cave.HEIGHT - 140);
        timeTexture.setScale(Cave.SCALE*1.5f);
        auraText = new Label("AURA: 0/0", labelStyle);
        auraText.setPosition(219, Cave.HEIGHT - 140);
        statusBelt = new Image(Assets.manager.get(Assets.statusBelt));
        statusBelt.setScale(Cave.SCALE*1.5f);
        statusBelt.setPosition(0, Cave.HEIGHT-statusBelt.getHeight()*2*1.5f);
        sword = new Image(Assets.manager.get(Assets.uiSword));
        sword.setScale(1.75f);
        sword.setPosition(38, Cave.HEIGHT - sword.getHeight()*2 - 10);
        sword.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                weapon = false;
                sword.remove();
                addActor(staff);
            }
        });
        staff = new Image(Assets.manager.get(Assets.uiStaff));
        staff.setScale(2.25f);
        staff.setPosition(25, Cave.HEIGHT - sword.getHeight()*2 - 40);
        staff.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                weapon = true;
                staff.remove();
                addActor(sword);
            }
        });
        addActor(statusBelt);
        addActor(sword);
        //addActor(staff);
        addActor(lifeTexture);
        addActor(magicTexture);
        addActor(timeTexture);
        addActor(auraText);
        addActor(lifeText);
        addActor(magicText);
        length = (int)timeTexture.getWidth();
    }

    private Texture CreateTexture(int width, int hight, int r, int g, int b, int a) {
        Texture tmp;
        Pixmap pixmap = new Pixmap(width * 4, hight, Pixmap.Format.RGB888);
        pixmap.setColor(r, g, b, 1);
        pixmap.fillRectangle(0, 0, width * 4, hight);
        tmp = new Texture(pixmap);
        pixmap.dispose();
        return tmp;
    }

    public boolean getWeapon() {
        return weapon;
    }

    public void update(int maxLife, int life,int maxMagic, int magic, int maxAura, int aura) {

        lifeTexture.setWidth((life/(float)maxLife)*100 + 1);
        magicTexture.setWidth((magic/(float)maxMagic)*100 + 1);
        timeTexture.setWidth((aura/(float)maxAura)*100 + 1);
        text.setText("FPS:" + Gdx.graphics.getFramesPerSecond());
        lifeText.setText("HP: " + life + "/" + maxLife);
        magicText.setText("MP: " + magic + "/" + maxMagic);
        auraText.setText("AURA: " + aura + "/" + maxAura);
    }
}
