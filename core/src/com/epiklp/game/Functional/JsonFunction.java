package com.epiklp.game.Functional;

import com.badlogic.gdx.utils.Json;

/**
 * Created by epiklp on 20.01.18.
 */

public class JsonFunction {
    public static void Create() {
        Game game = new Game();
        Player player = new Player();
        Json json = new Json();         //układa zmienne automatycznie
        json.setUsePrototypes(false);
        System.out.println(json.prettyPrint(game));
        System.out.println(json.prettyPrint(player));
    }

    public static boolean Read() {

        return false;
    }
}

class Game
{
    private boolean sound;
    private boolean effect;
    private String language;
    Game(){
        sound = true;
        effect = true;
        language = "en";
    }
}

class Player
{
    int level;
    float experience;
    int strengh;
    int life;
    int magic;
    float attackSpeed;
    Player() {
        level = 1;
        experience = 0;
        strengh = 10;
        life = 100;
        magic = 100;
        attackSpeed = 0.7f;
    }
}
