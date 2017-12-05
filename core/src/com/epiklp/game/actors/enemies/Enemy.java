package com.epiklp.game.actors.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.epiklp.game.TheBox;
import com.epiklp.game.actors.GameCharacter;

/**
 * Created by epiklp on 19.11.17.
 *
 *
 *
 * Wszystkie wartości dla attackRange oraz watchRange podawac w jednostkach Box2d!
 */

public abstract class Enemy extends GameCharacter {
    protected float   chanceOfDrop;
    protected float   attackRange;
    protected float   watchRange;
    protected boolean following;
    protected Vector2 heroPos;

    public Enemy(Sprite sprite) {
        super(sprite);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        moving();

//        if(body.getLinearVelocity().x > 0)
//            sprite.flip(true, false);
//        else if(body.getLinearVelocity().x < 0)
//           sprite.flip(false, false);

    }


    protected void moving(){
        if(following){
            followHero();
        }else{
            watching();
        }
    }
    float pos = 0;
    protected void watching(){
        pos += speedWalk/100;
        Vector2 p = new Vector2(watchRange * MathUtils.sin(pos),0);
        body.setLinearVelocity(p);

    }

    protected void followHero(){
        if (heroPos.x > body.getPosition().x) {
            body.setLinearVelocity(speedWalk, 0);
        }else if (heroPos.x < body.getPosition().x) {
            body.setLinearVelocity(-speedWalk, 0);
        }
    }

    //return this, only for shorter record in MyContactListener
    public Enemy setFollowing(boolean following) {
        this.following = following;
        return this;
    }

    public void setHeroPos(Vector2 pos){
        this.heroPos = pos;
    }


    public float getChanceOfDrop() {
        return chanceOfDrop;
    }

}
