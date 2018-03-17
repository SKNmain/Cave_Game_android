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
    private Label text, lifeText, magicText, auraText, lvText, coinText;
    private Label.LabelStyle labelStyle;
    private Image lifeTexture, magicTexture, auraTexture;
    private Image heart, mana, aura, empty[];
    private Image sword, staff;
    private boolean weapon;


    public UI() {
        weapon = false;
        labelStyle = new Label.LabelStyle(Assets.smallFont, Color.WHITE);
        text = new Label("FPS:" + Gdx.graphics.getFramesPerSecond(), labelStyle);
        text.setPosition(0, Cave.HEIGHT /2);
        addActor(text);
        empty = new Image[4];
        for(int i=0; i<3; i++) {
            empty[i] = new Image(Assets.MANAGER.get(Assets.EMPTY_BAR));
            empty[i].setScale(2);
            empty[i].setPosition(32 + 210*i, Cave.HEIGHT - 68);
            addActor(empty[i]);
        }
        lifeTexture = new Image(Assets.MANAGER.get(Assets.STATUS_HP));
        lifeTexture.setScale(2);
        lifeTexture.setPosition(32, Cave.HEIGHT - 68);
        lifeText = new Label("HP: 0/0", labelStyle);
        lifeText.setPosition(64, Cave.HEIGHT - 48);
        addActor(lifeTexture);
        addActor(lifeText);
        heart = new Image(Assets.MANAGER.get(Assets.HEART));
        heart.setScale(2);
        heart.setPosition(0, Cave.HEIGHT-64);
        addActor(heart);
        magicTexture = new Image(Assets.MANAGER.get(Assets.STATUS_MANA));
        magicTexture.setScale(2);
        magicTexture.setPosition(242, Cave.HEIGHT - 68);
        magicText = new Label("0/0", labelStyle);
        magicText.setPosition(274, Cave.HEIGHT - 48);
        addActor(magicTexture);
        addActor(magicText);
        mana = new Image(Assets.MANAGER.get(Assets.MANA));
        mana.setScale(2);
        mana.setPosition(210, Cave.HEIGHT-64);
        addActor(mana);
        auraTexture = new Image(Assets.MANAGER.get(Assets.STATUS_AURA));
        auraTexture.setScale(2);
        auraTexture.setPosition(452, Cave.HEIGHT - 68);
        auraText = new Label("0/0", labelStyle);
        auraText.setPosition(484, Cave.HEIGHT - 48);
        addActor(auraTexture);
        addActor(auraText);
        aura = new Image(Assets.MANAGER.get(Assets.AURA));
        aura.setScale(2);
        aura.setPosition(420, Cave.HEIGHT-64);
        addActor(aura);
        empty[3] = new Image(Assets.MANAGER.get(Assets.EMPTY_BAR));
        empty[3].setScale(2);
        empty[3].setPosition(32, Cave.HEIGHT - 128);
        addActor(empty[3]);
        lvText = new Label("lv: 0", labelStyle);
        lvText.setPosition(36, Cave.HEIGHT - 104);
        addActor(lvText);

        coinText = new Label("coin: 0", labelStyle);
        coinText.setPosition(lvText.getWidth()+46, Cave.HEIGHT - 104);
        addActor(coinText);
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

    public void update(int maxLife, int life,int maxMagic, int magic, int maxAura, int aura, int coin, int lv, int actExp, int maxExp) {

        lifeTexture.setWidth((life/(float)maxLife)*84);
        lifeText.setText(life + "/" + maxLife);
        magicTexture.setWidth((magic/(float)maxMagic)*84);
        magicText.setText(magic + "/" + maxMagic);
        auraTexture.setWidth((aura/(float)maxAura)*84);
        auraText.setText(aura + "/" + maxAura);
        lvText.setText("lv:" +lv );
        coinText.setText("coin:" + coin);
        text.setText("FPS:" + Gdx.graphics.getFramesPerSecond());
    }
}
