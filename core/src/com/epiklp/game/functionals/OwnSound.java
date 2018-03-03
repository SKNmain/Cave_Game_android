package com.epiklp.game.functionals;

import com.badlogic.gdx.audio.Music;

/**
 * Created by epiklp on 07.02.18.
 */

public class OwnSound {
    public static boolean MUSIC;
    public static boolean EFFECTS;
    private static Music menuMusic;
    //private Music menuMusic, gameMusic;

    public static void initialize(){
        menuMusic = Assets.manager.get(Assets.menuMusic);
        menuMusic.setLooping(true);
    }


    public static void play(String music) {
        if(music.equals("menuMusic") && MUSIC == true ){
            menuMusic.play();
            menuMusic.setVolume(0.4f);
        }
    }

    public static void update(String music)
    {
        if(music.equals("menuMusic") && MUSIC == true )
            menuMusic.play();
        else
            menuMusic.stop();
    }


    public static void dispose() {
        menuMusic.dispose();
    }
}
