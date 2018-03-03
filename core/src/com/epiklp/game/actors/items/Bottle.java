package com.epiklp.game.actors.items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.epiklp.game.actors.GameObject;
import com.epiklp.game.actors.characters.Hero;
import com.epiklp.game.functionals.Assets;
import com.epiklp.game.functionals.b2d.BodyCreator;
import com.epiklp.game.functionals.b2d.TheBox;

import java.util.Random;

/**
 * Created by Asmei on 2018-02-12.
 */

public class Bottle extends GameObject {

    String type;
    String size;


    //type must be "hp" or "mp"
    //sizeOfBottle must be "big", "medium" or "small"
    public Bottle(float x, float y, String type, String sizeOfBottle) {
        super(Assets.manager.get(Assets.textureAtlas).createSprite(type + "_" + sizeOfBottle,-1), 16, 20);
        this.type = type;
        this.size = sizeOfBottle;
        body = BodyCreator.createBody(x, y, false);
        light = TheBox.createPointLight(body, 32, new Color(0.5f, 0.5f , 1f, 0.7f), false, 4, -2, -2);

        switch(size){
            case "big":
                BodyCreator.createBoxSensor(body, 14f, 20f);
                break;
            case "medium":
                BodyCreator.createBoxSensor(body, 10f, 20f);
                break;
            case "small":
                BodyCreator.createBoxSensor(body, 8f, 18f);
                break;
        }
        body.setUserData(this);

//        light = TheBox.createPointLight(body, 64, new Color(1.000f, 0.498f, 0.314f, .9f), 12, 0, 0);
        body.setGravityScale(0);
}

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void use(Hero hero){
        int points = 0;
        switch(size){
            case "big":
                points = 50; break;
            case "medium":
                points = 25; break;
            case "small":
                points = 10; break;
        }
        if(type.equals("hp"))
            hero.setActLife(points);
        else
            hero.setActMana(points);
    }

    @Override
    public void initStats() {

    }
}
