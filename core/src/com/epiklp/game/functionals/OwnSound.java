package com.epiklp.game.functionals;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.epiklp.game.Cave;

/**
 * Created by epiklp on 07.02.18.
 */

public class OwnSound {
    public static boolean MUSIC;
    public static boolean EFFECTS;
    private static Music menuMusic, gameMusic;

    public static void initialize(){
        menuMusic = Assets.MANAGER.get(Assets.menuMusic);
        menuMusic.setLooping(true);
        gameMusic = Assets.MANAGER.get(Assets.gameMusic);
        gameMusic.setLooping(true);
    }

    public static void firstPlay()
    {
        if( Cave.CaveState.equals(Cave.CaveGame.MENU) && MUSIC){
            menuMusic.play();
            menuMusic.setVolume(0.4f);
        }
    }

    public static void play() {
        if( Cave.CaveState.equals(Cave.CaveGame.MENU)){
            menuMusic.play();
            menuMusic.setVolume(0.4f);
        }
        if( Cave.CaveState.equals(Cave.CaveGame.GAME)){
            gameMusic.play();
            gameMusic.setVolume(1f);
        }
    }

    public static void stop() {
        if(Cave.CaveState.equals(Cave.CaveGame.MENU)){
            menuMusic.stop();
        }
        else {
            gameMusic.stop();
        }
    }

    public static void playEffect(AssetDescriptor<Sound> sfx, float volume) {
        if(EFFECTS)
            Assets.MANAGER.get(sfx).play(volume);
    }


    public static void dispose() {
        menuMusic.dispose();
        gameMusic.dispose();
    }
}
