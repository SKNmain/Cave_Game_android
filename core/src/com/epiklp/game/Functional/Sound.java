package com.epiklp.game.Functional;

import com.badlogic.gdx.audio.Music;

/**
 * Created by epiklp on 07.02.18.
 */

public class Sound {
    public static boolean MUSIC;
    public static boolean EFFECTS;
    private static Music menuMusic;
    //private Music menuMusic, gameMusic;

    public static void initialize(){
        menuMusic = Assets.manager.get(Assets.menuMusic);
        menuMusic.setLooping(true);
    }


    public static void play(String music) {
        if(music.equals("menuMusic") && MUSIC == true )
            menuMusic.play();
    }

    public static void pause(String music) {
        if(music.equals("menuMusic") && MUSIC == true )
            menuMusic.pause();
    }


    public static void stop(String music) {
        if(music.equals("menuMusic"))
            menuMusic.stop();
    }


    public static void dispose() {
        menuMusic.dispose();
    }
}
