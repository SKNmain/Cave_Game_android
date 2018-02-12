package com.epiklp.game.Functional;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.epiklp.game.Cave;
import com.epiklp.game.actors.characters.GameCharacter;

/**
 * Created by Asmei on 2018-02-09.
 */

public class AnimationCore {
    private ArrayMap<Animation<Sprite>, GameCharacter.STATE> animationsMap;
    private float frameDuration = 0.15f;
    private float sizeX;
    private float sizeY;
    private float elapsedTime;


    public AnimationCore() {
        sizeX = 1f;
        sizeY = 1f;
        elapsedTime = 0;
    }

    public AnimationCore(float sizeX, float sizeY) {
        animationsMap = new ArrayMap<Animation<Sprite>, GameCharacter.STATE>();
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public void addNewFrames(float frameDuration, Array<Sprite> sprites, GameCharacter.STATE state, Animation.PlayMode playMode) {
        for(Sprite spr : sprites)
            spr.setSize(sizeX * Cave.SCALE, sizeY * Cave.SCALE);
        Animation<Sprite> anim = new Animation<Sprite>(frameDuration, sprites, playMode);
        animationsMap.put(anim, state);
    }

    public void addNewFrames(Array<Sprite> sprites, GameCharacter.STATE state, Animation.PlayMode playMode) {
        addNewFrames(this.frameDuration, sprites, state, playMode);
    }

    public Sprite getFrame(float elapsedTime, boolean turn, GameCharacter.STATE state) {
        if (animationsMap.size == 1) return animationsMap.getKeyAt(0).getKeyFrame(elapsedTime);

        Sprite sprite = animationsMap.getKey(state, true).getKeyFrame(elapsedTime);
        return sprite;
    }



}
