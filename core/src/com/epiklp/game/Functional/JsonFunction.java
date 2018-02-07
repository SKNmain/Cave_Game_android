package com.epiklp.game.Functional;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;


/**
 * Created by epiklp on 20.01.18.
 */

public class JsonFunction {
    public static Json json;
    public static One one;

    public static void Create(FileHandle file) {
        one = new One();
        System.out.println(json.prettyPrint(one));
        json.toJson(one,file);
    }

    public static void Read(FileHandle file) {
        one = json.fromJson(One.class, Gdx.files.local("option.json"));
    }

    public static void initzialie() {
        json = new Json();
        json.setUsePrototypes(false);
    }
}

class One
{
    Game game = new Game();
    Player player = new Player();
}

class Game
{
    boolean sound = false;
    boolean effect = true;
    String language = "en";
}

class Player
{
    int level = 1;
    float experience = 0;
    int strengh = 10;
    int maxlife = 100;
    int life = 100;
    int magic = 100;
    float attackSpeed = 0.7f;
}
